/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.RenameMe;
import entities.Role;
import entities.User;
import facades.KrakFacadeIMPL;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static rest.LoginEndpointTest.startServer;
import utils.EMF_Creator;

/**
 *
 * @author Malte
 */
public class KrakResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static RenameMe r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    private static KrakFacadeIMPL facade;
    private static Person p1;
    private static Person p2;
    private static Person p3;
    private static Person p4;
    private static Hobby h1;
    private static Hobby h2;
    private static Hobby h3;
    private static Hobby h4;
    private static Hobby h5;
    private static Address a1;
    private static Address a2;
    private static Address a3;

    public KrakResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        // Grizzly Start
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        // Grizzly End

        facade = KrakFacadeIMPL.getKrakFacade(emf);
        Address a0 = new Address("vej1", "by1", 1000);
        a1 = new Address("Jyskvej 1", "Vejle", 2100);
        a2 = new Address("Mindevej 44", "Aalborg", 9000);
        a3 = new Address("Silkeborgvej 22", "Silkeborg", 8600);

        p1 = new Person("p1@p1.dk", "12345678", "Lars", "Larsen", a1);
        p2 = new Person("p2@p2.dk", "82067263", "Marete", "Larsen", a1);
        p3 = new Person("p3@p3.dk", "84673666", "Kim", "Larsen", a2);
        p4 = new Person("p4@p4.dk", "55555555", "Fætter Johnny", "Larsen", a3);

        h1 = new Hobby("Lacrosse", "Rich people");
        h2 = new Hobby("Golf", "Very Rich People");
        h3 = new Hobby("Running", "Poor people");
        h4 = new Hobby("Swimming", "Old people");
        h5 = new Hobby("Jumping", "Small people");

        p1.addHobby(h1);
        p1.addHobby(h2);
        p1.addHobby(h3);

        p2.addHobby(h1);
        p2.addHobby(h2);
        p2.addHobby(h4);

        p3.addHobby(h5);

        p4.addHobby(h1);
        p4.addHobby(h2);
        p4.addHobby(h3);
        p4.addHobby(h4);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Users start
        // Delete existing users and roles to get a "fresh" database
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from Role").executeUpdate();

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        User user = new User("user", "test");
        user.addRole(userRole);
        User admin = new User("admin", "test");
        admin.addRole(adminRole);
        User both = new User("user_admin", "test");
        both.addRole(userRole);
        both.addRole(adminRole);
        User nobody = new User("nobody", "test"); //no role connected
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.persist(nobody);
        System.out.println("Saved test data to database");
        // Users end

        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(h1);
        em.persist(h2);
        em.persist(h3);
        em.persist(h4);
        em.persist(h5);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        // Log out
        logOut();
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    /*
    @Test
    public void testRestForAdmin() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: admin"));
    }
     */
    /**
     * Test of getPersonsByHobby method, of class KrakResource.
     */
    @Test
    public void testGetPersonsByHobby() {
        System.out.println("getPersonsByHobby");
        login("user", "test");

        // Arrange
        // p4, p2, p1 expected from h2
        List<PersonDTO> expResult = new ArrayList();
        expResult.add(new PersonDTO(p1));
        expResult.add(new PersonDTO(p2));
        expResult.add(new PersonDTO(p4));

        // Act
        JsonPath jsonPath = RestAssured.given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when().request("GET", "krak/hobby/Golf").then()
                .assertThat().log().body()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .extract().body().jsonPath();

        List<PersonDTO> result = jsonPath.getList("", PersonDTO.class);

        //Assert
        assertThat(result, containsInAnyOrder(expResult.toArray()));

    }
    
    /**
     * Test of adminDeletePerson method, of class KrakResource.
     */
    @Test
    public void testAdminDeletePerson() {
        System.out.println("adminDeletePerson");
        // p1 og p2 bor på samme vej: a1
        login("admin", "test");
        
        // Arrange
        PersonDTO expResult = new PersonDTO(p1);
        
        PersonDTO result
                = with()
                        .contentType("application/json")
                        .accept(ContentType.JSON)
                        .header("x-access-token", securityToken)
                        .when().request("DELETE", "krak/admin/person/delete/"+expResult.getId()).then()
                        .assertThat().log().body()
                        .statusCode(HttpStatus.OK_200.getStatusCode())
                        .extract()
                        .as(PersonDTO.class); //extract result JSON as object

        //Assert
        MatcherAssert.assertThat((result), equalTo(expResult));
        
    }
    
    /**
     * Test of adminEditPerson method, of class KrakResource.
     */
    @Test
    public void testAdminEditPerson() {
        System.out.println("adminEditPerson");
        login("admin", "test");

        // Arrange
        PersonDTO expResult = new PersonDTO(p3);
        expResult.setfName("Lotte");
        expResult.setlName("Hansen");

        PersonDTO result
                = with()
                        .body(expResult)
                        .contentType("application/json")
                        .accept(ContentType.JSON)
                        .header("x-access-token", securityToken)
                        .when().request("PUT", "krak/admin/person/edit").then()
                        .assertThat().log().body()
                        .statusCode(HttpStatus.OK_200.getStatusCode())
                        .extract()
                        .as(PersonDTO.class);//extract result JSON as object

        //Assert
        MatcherAssert.assertThat((result), equalTo(expResult));

    }
//
//    /**
//     * Test of getPersonByID method, of class KrakResource.
//     */
//    @Test
//    public void testGetPersonByID() {
//        System.out.println("getPersonByID");
//        int id = 0;
//        KrakResource instance = new KrakResource();
//        PersonDTO expResult = null;
//        PersonDTO result = instance.getPersonByID(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPersonByEmail method, of class KrakResource.
//     */
//    @Test
//    public void testGetPersonByEmail() {
//        System.out.println("getPersonByEmail");
//        String email = "";
//        KrakResource instance = new KrakResource();
//        PersonDTO expResult = null;
//        PersonDTO result = instance.getPersonByEmail(email);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPersonByPhone method, of class KrakResource.
//     */
//    @Test
//    public void testGetPersonByPhone() {
//        System.out.println("getPersonByPhone");
//        String phone = "";
//        KrakResource instance = new KrakResource();
//        PersonDTO expResult = null;
//        PersonDTO result = instance.getPersonByPhone(phone);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllHobbies method, of class KrakResource.
//     */
//    @Test
//    public void testGetAllHobbies() {
//        System.out.println("getAllHobbies");
//        KrakResource instance = new KrakResource();
//        List<HobbyDTO> expResult = null;
//        List<HobbyDTO> result = instance.getAllHobbies();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of adminAddHobby method, of class KrakResource.
//     */
//    @Test
//    public void testAdminAddHobby() {
//        System.out.println("adminAddHobby");
//        HobbyDTO hobby = null;
//        KrakResource instance = new KrakResource();
//        HobbyDTO expResult = null;
//        HobbyDTO result = instance.adminAddHobby(hobby);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of adminEditHobby method, of class KrakResource.
//     */
//    @Test
//    public void testAdminEditHobby() {
//        System.out.println("adminEditHobby");
//        HobbyDTO hobby = null;
//        KrakResource instance = new KrakResource();
//        HobbyDTO expResult = null;
//        HobbyDTO result = instance.adminEditHobby(hobby);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of adminDeleteHobby method, of class KrakResource.
//     */
//    @Test
//    public void testAdminDeleteHobby() {
//        System.out.println("adminDeleteHobby");
//        int id = 0;
//        KrakResource instance = new KrakResource();
//        HobbyDTO expResult = null;
//        HobbyDTO result = instance.adminDeleteHobby(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of adminAddPerson method, of class KrakResource.
//     */
//    @Test
//    public void testAdminAddPerson() {
//        System.out.println("adminAddPerson");
//        PersonDTO person = null;
//        KrakResource instance = new KrakResource();
//        PersonDTO expResult = null;
//        PersonDTO result = instance.adminAddPerson(person);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }


}

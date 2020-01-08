package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author runin
 */
public class KrakFacadeIMPLTest {

    private static EntityManagerFactory emf;
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

    public KrakFacadeIMPLTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
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
    }

    /**
     * Test of getPersonByID method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonByID() {
        int id = p1.getId();
        Person expResult = p1;
        Person result = new Person(facade.getPersonByID(id));
        assertEquals(expResult, result);
    }

    /**
     * Test of getPersonByEmail method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonByEmail() {
        String email = p1.getEmail();
        Person expResult = p1;
        Person result = new Person(facade.getPersonByEmail(email));
        assertEquals(expResult, result);
    }

    /**
     * Test of getPersonByPhone method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonByPhone() {
        String phone = p1.getPhone();
        Person expResult = p1;
        Person result = new Person(facade.getPersonByPhone(phone));
        assertEquals(expResult, result);
    }

    /**
     * Test of getPersonsByHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonsByHobby() {
        System.out.println("getPersonsByHobby");

        List<PersonDTO> expResult = new ArrayList();
        expResult.add(new PersonDTO(p1));
        expResult.add(new PersonDTO(p2));
        expResult.add(new PersonDTO(p4));
        List<PersonDTO> result = facade.getPersonsByHobby(new HobbyDTO(h2));

        assertThat("Comparing two lists of people by Hobby", result, containsInAnyOrder(expResult.toArray()));
    }

    /**
     * Test of getAllHobbies method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetAllHobbies() {
        System.out.println("getAllHobbies");
        List<HobbyDTO> expResult = new ArrayList();
        expResult.add(new HobbyDTO(h1));
        expResult.add(new HobbyDTO(h2));
        expResult.add(new HobbyDTO(h3));
        expResult.add(new HobbyDTO(h4));
        expResult.add(new HobbyDTO(h5));
        List<HobbyDTO> result = facade.getAllHobbies();
        assertThat(result, containsInAnyOrder(expResult.toArray()));
    }

    /**
     * Test of adminAddHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminAddHobby() {
        System.out.println("adminAddHobby");
        HobbyDTO expResult = new HobbyDTO(new Hobby("Snacking", "Eating snacks"));
        HobbyDTO result = facade.adminAddHobby(expResult);
        assertEquals(expResult, result);
    }

    /**
     * Test of adminEditHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminEditHobby() {
        System.out.println("adminEditHobby");
        HobbyDTO expResult = new HobbyDTO(h4);
        expResult.setHobbyDescription("For all people");
        HobbyDTO result = facade.adminEditHobby(expResult);
        assertEquals(expResult, result);
    }

    /**
     * Test of adminDeleteHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminDeleteHobby() {
        System.out.println("adminDeleteHobby");
        int id = h2.getId();
        HobbyDTO expResult = new HobbyDTO(h2);
        HobbyDTO result = facade.adminDeleteHobby(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of adminAddPerson method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminAddPerson() {
        System.out.println("adminAddPerson");
        PersonDTO person = new PersonDTO(new Person("mand@menneske.dk", "50999080", "Barack", "Obama", new Address("Vej nr. 1", "Byen nr. 1", 1234)));
        PersonDTO expResult = person;
        PersonDTO result = facade.adminAddPerson(person);
        assertEquals(expResult, result);
    }

    /**
     * Test of adminEditPerson method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminEditPerson() {
        System.out.println("adminEditPerson");
        PersonDTO expResult = new PersonDTO(p1);
        expResult.setfName("Dette navn findes ikke");
        PersonDTO result = facade.adminEditPerson(expResult);
        assertEquals(expResult, result);
    }

    /**
     * Test of adminDeletePerson method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminDeletePerson() {
        System.out.println("adminDeletePerson");
        int id = p3.getId();
        PersonDTO expResult = new PersonDTO(p3);
        PersonDTO result = facade.adminDeletePerson(id);
        assertEquals(expResult, result);
    }
}

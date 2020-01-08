package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author runin
 */
public class KrakFacadeIMPLTest {
    
    public KrakFacadeIMPLTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getKrakFacade method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetKrakFacade() {
        System.out.println("getKrakFacade");
        EntityManagerFactory _emf = null;
        KrakFacadeIMPL expResult = null;
        KrakFacadeIMPL result = KrakFacadeIMPL.getKrakFacade(_emf);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonByID method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonByID() {
        System.out.println("getPersonByID");
        int id = 0;
        KrakFacadeIMPL instance = null;
        PersonDTO expResult = null;
        PersonDTO result = instance.getPersonByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonByEmail method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonByEmail() {
        System.out.println("getPersonByEmail");
        String email = "";
        KrakFacadeIMPL instance = null;
        PersonDTO expResult = null;
        PersonDTO result = instance.getPersonByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonByPhone method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonByPhone() {
        System.out.println("getPersonByPhone");
        String phone = "";
        KrakFacadeIMPL instance = null;
        PersonDTO expResult = null;
        PersonDTO result = instance.getPersonByPhone(phone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonsByHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetPersonsByHobby() {
        System.out.println("getPersonsByHobby");
        HobbyDTO hobby = null;
        KrakFacadeIMPL instance = null;
        List<PersonDTO> expResult = null;
        List<PersonDTO> result = instance.getPersonsByHobby(hobby);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllHobbies method, of class KrakFacadeIMPL.
     */
    @Test
    public void testGetAllHobbies() {
        System.out.println("getAllHobbies");
        KrakFacadeIMPL instance = null;
        List<HobbyDTO> expResult = null;
        List<HobbyDTO> result = instance.getAllHobbies();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adminAddHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminAddHobby() {
        System.out.println("adminAddHobby");
        HobbyDTO hobby = null;
        KrakFacadeIMPL instance = null;
        HobbyDTO expResult = null;
        HobbyDTO result = instance.adminAddHobby(hobby);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adminEditHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminEditHobby() {
        System.out.println("adminEditHobby");
        HobbyDTO hobby = null;
        KrakFacadeIMPL instance = null;
        HobbyDTO expResult = null;
        HobbyDTO result = instance.adminEditHobby(hobby);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adminDeleteHobby method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminDeleteHobby() {
        System.out.println("adminDeleteHobby");
        int id = 0;
        KrakFacadeIMPL instance = null;
        HobbyDTO expResult = null;
        HobbyDTO result = instance.adminDeleteHobby(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adminAddPerson method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminAddPerson() {
        System.out.println("adminAddPerson");
        PersonDTO person = null;
        KrakFacadeIMPL instance = null;
        PersonDTO expResult = null;
        PersonDTO result = instance.adminAddPerson(person);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adminEditPerson method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminEditPerson() {
        System.out.println("adminEditPerson");
        PersonDTO person = null;
        KrakFacadeIMPL instance = null;
        PersonDTO expResult = null;
        PersonDTO result = instance.adminEditPerson(person);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of adminDeletePerson method, of class KrakFacadeIMPL.
     */
    @Test
    public void testAdminDeletePerson() {
        System.out.println("adminDeletePerson");
        int id = 0;
        KrakFacadeIMPL instance = null;
        PersonDTO expResult = null;
        PersonDTO result = instance.adminDeletePerson(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

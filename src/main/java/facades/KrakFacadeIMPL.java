/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author
 */
public class KrakFacadeIMPL implements KrakFacadeInterface
{
    private static KrakFacadeIMPL facade;
    private static EntityManagerFactory emf;

    private KrakFacadeIMPL() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static KrakFacadeIMPL getKrakFacade(EntityManagerFactory _emf) {
        if (facade == null) {
            emf = _emf;
            facade = new KrakFacadeIMPL();
        }
        return facade;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO getPersonByID(int id)
    {
        EntityManager em = getEntityManager();
        try {
            return new PersonDTO(em.createNamedQuery("Person.getPersonByID", Person.class).setParameter("id", id).getSingleResult());
        } catch (NoResultException e) {
            throw new WebApplicationException("No user with that ID exists", 404);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }
    @Override
    public PersonDTO getPersonByEmail(String email)
    {
        EntityManager em = getEntityManager();
        try {
            return new PersonDTO(em.createNamedQuery("Person.getPersonByEmail", Person.class).setParameter("email", email).getSingleResult());
        } catch (NoResultException e) {
            throw new WebApplicationException("No user with that email exists", 404);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }
    
    @Override
    public PersonDTO getPersonByPhone(String phone)
    {
        EntityManager em = getEntityManager();
        try {
            return new PersonDTO(em.createNamedQuery("Person.getPersonByPhone", Person.class).setParameter("phone", phone).getSingleResult());
        } catch (NoResultException e) {
            throw new WebApplicationException("No user with that phone number exists", 404);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonsByHobby(HobbyDTO hobby)
    {
        EntityManager em = getEntityManager();
        try {
            List<Person> persons = em.createNamedQuery("Person.getPersonsByHobby").setParameter("name", hobby.getHobbyName()).getResultList();
            List<PersonDTO> result = new ArrayList();
            for (Person p : persons)
            {
                result.add(new PersonDTO(p));
            }
            return result;
        } catch (NoResultException e) {
            throw new WebApplicationException("No people with given hobby exists", 404);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public List<HobbyDTO> getAllHobbies()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HobbyDTO adminAddHobby(HobbyDTO hobby)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HobbyDTO adminEditHobby(HobbyDTO hobby)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HobbyDTO adminDeleteHobby(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO adminAddPerson(PersonDTO person)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO adminEditPerson(PersonDTO person)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonDTO adminDeletePerson(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

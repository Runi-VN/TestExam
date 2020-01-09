/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.AddressDTO;
import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author
 */
public class KrakFacadeIMPL implements KrakFacadeInterface {

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
    public PersonDTO getPersonByID(int id) {
        EntityManager em = getEntityManager();
        try {
            return new PersonDTO(em.createNamedQuery("Person.getPersonByID", Person.class).setParameter("id", id).getSingleResult());
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("No user with that ID exists", 404);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), 400);
        }
    }

    @Override
    public PersonDTO getPersonByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            return new PersonDTO(em.createNamedQuery("Person.getPersonByEmail", Person.class).setParameter("email", email).getSingleResult());
        } catch (NoResultException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("No user with that email exists", 404);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPersonByPhone(String phone) {
        EntityManager em = getEntityManager();
        try {
            return new PersonDTO(em.createNamedQuery("Person.getPersonByPhone", Person.class).setParameter("phone", phone).getSingleResult());
        } catch (NoResultException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("No user with that phone number exists", 404);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonsByHobby(String hobbyName) {
        EntityManager em = getEntityManager();
        try {
            List<Person> persons = em.createNamedQuery("Person.getPersonsByHobby").setParameter("name", hobbyName).getResultList();
            List<PersonDTO> result = new ArrayList();
            for (Person p : persons) {
                result.add(new PersonDTO(p));
            }
            return result;
        } catch (NoResultException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("No people with given hobby exists", 404);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = getEntityManager();
        try {
            List<Hobby> hobbies = em.createNamedQuery("Hobby.getAll").getResultList();
            List<HobbyDTO> result = new ArrayList();
            for (Hobby h : hobbies) {
                result.add(new HobbyDTO(h));
            }
            return result;
        } catch (NoResultException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("No hobbies found", 404);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyDTO adminAddHobby(HobbyDTO hobby) {
        if (hobby == null || hobby.getHobbyDescription() == null || hobby.getHobbyName() == null) {
            throw new WebApplicationException("Invalid hobby input", 400);
        }
        Hobby result = new Hobby(hobby);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            return new HobbyDTO(result);
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Hobby already exists", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while adding hobby", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyDTO adminEditHobby(HobbyDTO hobby) {
        if (hobby == null || hobby.getHobbyDescription() == null || hobby.getHobbyName() == null) {
            throw new WebApplicationException("Invalid hobby input on check", 400);
        }
        Hobby result = new Hobby(hobby);
        EntityManager em = getEntityManager();
        try {
            //em.find(Hobby.class, result.getId());
            em.getTransaction().begin();
            result = em.merge(result);
            em.getTransaction().commit();
            return new HobbyDTO(result);
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Hobby does not comply with database standards", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while editing hobby", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyDTO adminDeleteHobby(int id) {
        Hobby result;
        EntityManager em = getEntityManager();
        try {
            result = em.find(Hobby.class, id);
            em.getTransaction().begin();
            em.remove(result);
            em.getTransaction().commit();
            return new HobbyDTO(result);
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Hobby does not comply with database standards", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while deleting hobby", 500);
        } finally {
            em.close();
        }
    }

    private Address checkAddress(AddressDTO address, EntityManager em) {
        try {
            return em.createNamedQuery("Address.specific", Address.class)
                    .setParameter("street", address.getRoad())
                    .setParameter("zip", address.getZipcode())
                    .setParameter("city", address.getTown()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public PersonDTO adminAddPerson(PersonDTO person) {
        if (person == null || person.getMail() == null || person.getResidence() == null
                || person.getTelephone() == null || person.getfName() == null || person.getlName() == null) {
            throw new WebApplicationException("Invalid person input", 400);
        }
        Person result = new Person(person);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Address check = checkAddress(person.getResidence(), em);
            if (check != null) {
                result.setAddress(check);
            } else {
                em.persist(new Address(person.getResidence()));
            }
            em.persist(result);
            em.getTransaction().commit();
            return new PersonDTO(result);
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Person already exists", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while adding person", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO adminEditPerson(PersonDTO person) {
        if (person == null || person.getMail() == null || person.getResidence() == null
                || person.getTelephone() == null || person.getfName() == null || person.getlName() == null || person.getId() <= 0) {
            throw new WebApplicationException("Invalid person input", 400);
        }
        EntityManager em = getEntityManager();
        Person result = new Person(person);
        try {
            em.getTransaction().begin();
            Address check = checkAddress(person.getResidence(), em);
            if (check != null) {
                result.setAddress(check);
            } else {
                em.persist(new Address(person.getResidence()));
            }
            em.merge(result);
            em.getTransaction().commit();
            return new PersonDTO(result);
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Failed to edit person", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while adding person", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO adminDeletePerson(int id) {
        Person result;
        EntityManager em = getEntityManager();
        try {
            result = em.find(Person.class, id);
            em.getTransaction().begin();
            em.remove(result);
            em.getTransaction().commit();
            return new PersonDTO(result);
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Person does not comply with database standards", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while deleting person", 500);
        } finally {
            em.close();
        }
    }
}

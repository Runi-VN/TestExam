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
import entities.Role;
import entities.User;
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
            } else { // This isn't necessary, since we have CascadeType.PERSIST
//                em.persist(new Address(person.getResidence()));
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

    public boolean populate() {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        
            em.getTransaction().begin();

            Person p1;
            Person p2;
            Person p3;
            Person p4;
            Hobby h1;
            Hobby h2;
            Hobby h3;
            Hobby h4;
            Hobby h5;
            Address a1;
            Address a2;
            Address a3;

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

            return true;
        } catch (Exception e) {
            System.out.println("_____________________________________");
            e.printStackTrace();
            System.out.println("_____________________________________");
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

}

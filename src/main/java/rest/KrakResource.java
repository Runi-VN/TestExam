package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HobbyDTO;
import dto.PersonDTO;
import utils.EMF_Creator;
import facades.KrakFacadeIMPL;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Path("krak")
public class KrakResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final KrakFacadeIMPL FACADE = KrakFacadeIMPL.getKrakFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public PersonDTO getPersonByID(@PathParam("id") int id) {
        return FACADE.getPersonByID(id);
    }

    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public PersonDTO getPersonByEmail(@PathParam("email") String email) {
        return FACADE.getPersonByEmail(email);
    }

    @GET
    @Path("phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public PersonDTO getPersonByPhone(@PathParam("phone") String phone) {
        return FACADE.getPersonByPhone(phone);
    }

    @GET
    @Path("hobby/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public List<PersonDTO> getPersonsByHobby(@PathParam("name") String name) {
        return FACADE.getPersonsByHobby(name);
    }

    @GET
    @Path("hobby/all")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public List<HobbyDTO> getAllHobbies() {
        return FACADE.getAllHobbies();
    }

    @POST
    @Path("admin/hobby/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public HobbyDTO adminAddHobby(HobbyDTO hobby) {
        return FACADE.adminAddHobby(hobby);
    }

    @PUT
    @Path("admin/hobby/edit")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public HobbyDTO adminEditHobby(HobbyDTO hobby) {
        return FACADE.adminEditHobby(hobby);
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("admin/hobby/delete/{id}")
    @RolesAllowed({"admin"})
    public HobbyDTO adminDeleteHobby(@PathParam("id") int id) {
        return FACADE.adminDeleteHobby(id);
    }

    @POST
    @Path("admin/person/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public PersonDTO adminAddPerson(PersonDTO person) {
        return FACADE.adminAddPerson(person);
    }

    @PUT
    @Path("admin/person/edit")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public PersonDTO adminEditPerson(PersonDTO person) {
        return FACADE.adminEditPerson(person);
    }

    @DELETE
    @Path("admin/person/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public PersonDTO adminDeletePerson(@PathParam("id") int id) {
        return FACADE.adminDeletePerson(id);
    }

    @GET
    @Path("testdata")
    @Produces({MediaType.APPLICATION_JSON})
    public String populateDatabase() {

        boolean success = FACADE.populate();

        if (success) {
            return "{\"message\":\"Database populated with dummy data\"}";
        } else {
            return "{\"message\":\"Failed to populate database\"}";
        }
    }

}

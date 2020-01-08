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
import javax.ws.rs.GET;
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
    @Path("user/id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public PersonDTO getPersonByID(@PathParam("id") int id) {
        return FACADE.getPersonByID(id);
    }

    @GET
    @Path("user/email/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public PersonDTO getPersonByEmail(@PathParam("email") String email) {
        return FACADE.getPersonByEmail(email);
    }

    @GET
    @Path("user/phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public PersonDTO getPersonByPhone(@PathParam("phone") String phone) {
        return FACADE.getPersonByPhone(phone);
    }

    @GET
    @Path("user/hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public List<PersonDTO> getPersonsByHobby(@PathParam("hobby") HobbyDTO hobby) {
        return FACADE.getPersonsByHobby(hobby);
    }
    

//    public List<HobbyDTO> getAllHobbies ();
//    public HobbyDTO adminAddHobby (HobbyDTO hobby);
//    public HobbyDTO adminEditHobby (HobbyDTO hobby);
//    public HobbyDTO adminDeleteHobby (int id);
//    public PersonDTO adminAddPerson (PersonDTO person);
//    public PersonDTO adminEditPerson (PersonDTO person);
//    public PersonDTO adminDeletePerson (int id);
}

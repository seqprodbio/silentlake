package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Registration;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.UserService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.UserDto;

@Stateless
@Path("/user")
public class UserResource {

   public static final String ID_PREFIX = "glse_user_id=";

   @EJB
   private UserService userService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public UserDto getUser(@PathParam("id") Integer id) {
      UserDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      for (Registration user : userService.getUsers()) {
         UserDto userDto = Dtos.asDto(user);
         Integer identity = getId(user.getInvitationCode());
         if (identity != null && identity == id) {
            userDto.setId(id);
            userDto.setUrl(baseUri.toString() + id);
            result = userDto;
         }
      }
      return result;
   }

   private Registration getRegistration(Integer id) {
      Registration result = null;
      for (Registration user : userService.getUsers()) {
         Integer identity = getId(user.getInvitationCode());
         if (identity != null && identity == id) {
            result = user;
         }
      }
      return result;
   }

   @PUT
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(@PathParam("id") Integer id, UserDto userDto) {
      Registration existingRegistration = getRegistration(id);
      if (existingRegistration == null) {
         Registration registration = Dtos.fromDto(userDto);
         registration.setInvitationCode(createId(id));
         userService.create(registration);
      } else {
         Registration registration = Dtos.fromDto(userDto, existingRegistration);
         userService.update(registration);
      }
   }

   static String createId(Integer id) {
      return ID_PREFIX + id;
   }

   static Integer getId(String idString) {
      Integer result = null;
      if (idString != null && idString.startsWith(ID_PREFIX)) {
         try {
            result = Integer.valueOf(idString.substring(ID_PREFIX.length()));
         } catch (NumberFormatException e) {
            // If no number is available we will leave the result as null.
         }
      }
      return result;
   }

}

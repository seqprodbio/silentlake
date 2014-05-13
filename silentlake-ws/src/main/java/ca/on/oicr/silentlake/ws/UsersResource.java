package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Registration;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.UserService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.UserDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/grape")
public class UsersResource {

   public static final String ID_PREFIX = "glse_user_id=";

   @EJB
   private UserService userService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/testuser")
   @Produces(MediaType.APPLICATION_JSON)
   public UserDto getTestUser() {
      Registration reg = new Registration();
      reg.setFirstName("Tony");
      reg.setLastName("DeBat");
      reg.setEmail("tony.debat@oicr.on.ca");
      reg.setInstitution("OICR");
      reg.setRegistrationId(42);
      return Dtos.asDto(reg);
   }

   @GET
   @Path("/users")
   @Produces(MediaType.APPLICATION_JSON)
   public List<UserDto> getUsers() {
      List<UserDto> users = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("grape/user/").build();
      for (Registration user : userService.getUsers()) {
         System.out.println("dooly " + user.getEmail());
         UserDto userDto = Dtos.asDto(user);
         Integer id = getId(user.getInvitationCode());
         if (id != null) {
            userDto.setId(id);
            userDto.setUrl(baseUri.toString() + id);
            users.add(userDto);
         }
      }
      return users;
   }

   @GET
   @Path("/root")
   @Produces(MediaType.TEXT_PLAIN)
   public String test() {
      return "hello world " + userService.getName();
   }

   @POST
   @Path("/foo/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(@PathParam("id") Integer id, UserDto userDto) {
      Registration registration = Dtos.fromDto(userDto);
      userService.create(registration);
      registration.setInvitationCode(createId(id));
      System.out.println("hello from default user add");
      System.out.println("id from object: " + registration.getRegistrationId());
      // System.out.println("id from super: " + registrationDao.super().id);

      // Install postman
      // POST user json to see if user is added.
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

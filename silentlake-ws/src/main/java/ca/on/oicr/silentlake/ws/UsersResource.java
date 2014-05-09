package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Registration;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.on.oicr.silentlake.service.UserService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.UserDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/grape")
public class UsersResource {

   @EJB
   private UserService userService;

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
      for (Registration user : userService.getUsers()) {
         System.out.println("dooly " + user.getEmail());
         users.add(Dtos.asDto(user));
      }
      return users;
   }

   @GET
   @Path("/root")
   @Produces(MediaType.TEXT_PLAIN)
   public String test() {
      return "hello world " + userService.getName();
   }

}

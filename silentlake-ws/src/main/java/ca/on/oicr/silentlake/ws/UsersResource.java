package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Registration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.UserDto;

@Path("/grape")
public class UsersResource {

   @GET
   @Path("/users")
   @Produces(MediaType.APPLICATION_JSON)
   public UserDto getUsers() {
      Registration reg = new Registration();
      reg.setFirstName("Tony");
      reg.setLastName("DeBat");
      reg.setEmail("tony.debat@oicr.on.ca");
      reg.setInstitution("OICR");
      reg.setRegistrationId(42);
      return Dtos.asDto(reg);
   }

   @GET
   @Path("/root")
   @Produces(MediaType.TEXT_PLAIN)
   public String test() {
      return "hello world";
   }

}

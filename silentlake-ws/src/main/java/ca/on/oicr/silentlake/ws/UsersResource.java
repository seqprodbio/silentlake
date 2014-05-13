package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Registration;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.UserService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.UserDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/users")
public class UsersResource {

   @EJB
   private UserService userService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<UserDto> getUsers() {
      List<UserDto> users = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      for (Registration user : userService.getUsers()) {
         UserDto userDto = Dtos.asDto(user);
         Integer id = UserResource.getId(user.getInvitationCode());
         if (id != null) {
            userDto.setId(id);
            userDto.setUrl(baseUri.toString() + id);
            users.add(userDto);
         }
      }
      return users;
   }

}

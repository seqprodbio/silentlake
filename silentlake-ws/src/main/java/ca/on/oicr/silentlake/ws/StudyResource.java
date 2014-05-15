package ca.on.oicr.silentlake.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.StudyService;

@Stateless
@Path("/study")
public class StudyResource {

   @EJB
   private StudyService studyService;

   @Context
   private UriInfo uriInfo;

   // @GET
   // @Path("/{id}")
   // @Produces(MediaType.APPLICATION_JSON)
   // public UserDto getStudy(@PathParam("id") Integer id) {
   // UserDto result = null;
   // final URI baseUri = uriInfo.getBaseUriBuilder().path("study/").build();
   //
   // for (Registration user : userService.getUsers()) {
   // UserDto userDto = Dtos.asDto(user);
   // Integer identity = getId(user.getInvitationCode());
   // if (identity != null && identity == id) {
   // userDto.setId(id);
   // userDto.setUrl(baseUri.toString() + id);
   // result = userDto;
   // }
   // }
   // return result;
   // }
}

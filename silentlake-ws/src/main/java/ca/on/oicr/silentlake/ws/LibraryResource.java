package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Sample;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.SampleDto;

@Stateless
@Path("/library")
public class LibraryResource {

   @EJB
   private SampleService sampleService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public SampleDto getLibrary(@PathParam("id") Integer id) {
      SampleDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("library/").build();

      Sample sample = sampleService.getLibrary(id);
      if (sample != null) {
         result = Dtos.asDto(sample);
         result.setId(id);
         result.setUrl(baseUri.toString() + id);
      }

      return result;
   }

   @PUT
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(@PathParam("id") Integer id, SampleDto sampleDto) {
      Sample existingLibrary = sampleService.getLibrary(id);
      if (existingLibrary == null) {
         Sample library = sampleService.fromDto(sampleDto);
         sampleService.create(library, id, sampleDto);
      } else {
         Sample library = sampleService.fromDto(sampleDto, existingLibrary);
         sampleService.update(library, sampleDto);
      }
   }

   @DELETE
   @Path("/{id}")
   public void remove(@PathParam("id") Integer id) {
      Sample library = sampleService.getLibrary(id);
      sampleService.remove(library);
   }
}

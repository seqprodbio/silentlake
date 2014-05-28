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
@Path("/sample")
public class SampleResource {

   @EJB
   private SampleService sampleService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public SampleDto getSample(@PathParam("id") Integer id) {
      SampleDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("sample/").build();

      Sample sample = sampleService.getSample(id);
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
      Sample existingSample = sampleService.getSample(id);
      if (existingSample == null) {
         Sample sample = sampleService.fromDto(sampleDto);
         sampleService.create(sample, id, sampleDto);
      } else {
         Sample sample = sampleService.fromDto(sampleDto, existingSample);
         sampleService.update(sample, sampleDto);
      }
   }

   @DELETE
   @Path("/{id}")
   public void remove(@PathParam("id") Integer id) {
      Sample sample = sampleService.getSample(id);
      sampleService.remove(sample);
   }
}

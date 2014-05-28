package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Sample;

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

import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.SampleDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/libraries")
public class LibrariesResource {

   private final String ID_KEY = "geo_template_id";

   @EJB
   private SampleService sampleService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<SampleDto> getLibraries() {
      List<SampleDto> libraries = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("library/").build();

      for (Sample library : sampleService.getLibraries(ID_KEY)) {
         SampleDto libraryDto = Dtos.asDto(library);
         Integer id = sampleService.getId(library.getSampleAttributeCollection(), ID_KEY);
         libraryDto.setId(id);
         libraryDto.setUrl(baseUri.toString() + id);
         libraries.add(libraryDto);
      }
      return libraries;
   }
}

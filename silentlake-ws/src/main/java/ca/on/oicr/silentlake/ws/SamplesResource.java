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
@Path("/samples")
public class SamplesResource {

   private final String ID_KEY = "geo_template_id";

   @EJB
   private SampleService sampleService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<SampleDto> getSamples() {
      List<SampleDto> samples = Lists.newArrayList();
      final URI baseInfo = uriInfo.getBaseUriBuilder().path("sample/").build();

      for (Sample sample : sampleService.getSamples(ID_KEY)) {
         SampleDto sampleDto = Dtos.asDto(sample);
         Integer id = sampleService.getId(sample.getSampleAttributeCollection(), ID_KEY);
         sampleDto.setId(id);
         sampleDto.setUrl(baseInfo.toString() + id);
         samples.add(sampleDto);
      }
      return samples;
   }
}

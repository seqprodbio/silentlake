package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Ius;
import io.seqware.webservice.generated.model.Lane;
import io.seqware.webservice.generated.model.SequencerRun;

import java.net.URI;
import java.util.List;

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

import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.service.SequencerRunService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.LaneDto;
import ca.on.oicr.silentlake.ws.dto.SequencerRunDto;
import ca.on.oicr.silentlake.ws.dto.SequencerSampleDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/sequencerrun")
public class SequencerRunResource {

   @EJB
   private SequencerRunService sequencerRunService;

   @EJB
   private SampleService sampleService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public SequencerRunDto getSequencerRun(@PathParam("id") Integer id) {
      SequencerRunDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("sequencerrun/").build();
      final URI sampleBaseUri = uriInfo.getBaseUriBuilder().path("sample/").build();

      SequencerRun sequencerRun = sequencerRunService.findSequencerRun(id);
      if (sequencerRun != null) {
         result = Dtos.asDto(sequencerRun);
         if (result.getPositions() != null) {
            for (LaneDto laneDto : result.getPositions()) {
               Lane correspondingLane = sequencerRunService.laneLookupByIndex(laneDto.getPosition(), sequencerRun.getLaneCollection());
               if (correspondingLane.getIusCollection() != null) {
                  List<SequencerSampleDto> sequencerSampleDtos = Lists.newArrayList();
                  for (Ius ius : correspondingLane.getIusCollection()) {
                     SequencerSampleDto sequencerSampleDto = Dtos.asDto(ius);
                     sequencerSampleDto.setSampleId(sampleService
                           .getId(ius.getSampleId().getSampleAttributeCollection(), "geo_template_id"));
                     sequencerSampleDto.setSampleUrl(sampleBaseUri.toString() + sequencerSampleDto.getSampleId());
                     sequencerSampleDtos.add(sequencerSampleDto);
                  }
                  laneDto.setSamples(sequencerSampleDtos);
               } else {
                  List<SequencerSampleDto> emptyList = Lists.newArrayList();
                  laneDto.setSamples(emptyList); // Because it wouldn't let me pass in List.newArrayList() as a parameter
               }
            }
         }
         result.setId(id);
         result.setUrl(baseUri.toString() + id);
      }

      return result;
   }

   @PUT
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public void putSequencerRun(@PathParam("id") Integer id, SequencerRunDto sequencerRunDto) {
      SequencerRun existingSequencerRun = sequencerRunService.findSequencerRun(id);

      if (existingSequencerRun == null) {
         SequencerRun newSequencerRun = sequencerRunService.fromDto(sequencerRunDto);
         sequencerRunService.create(newSequencerRun, sequencerRunDto, id);
      } else {
         SequencerRun updatedSequencerRun = sequencerRunService.fromDto(sequencerRunDto, existingSequencerRun);
         sequencerRunService.update(updatedSequencerRun, sequencerRunDto);
      }
   }
}

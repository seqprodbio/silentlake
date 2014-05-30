package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Ius;
import io.seqware.webservice.generated.model.Lane;
import io.seqware.webservice.generated.model.SequencerRun;

import java.net.URI;
import java.util.Collection;
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
import ca.on.oicr.silentlake.service.SequencerRunService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.LaneDto;
import ca.on.oicr.silentlake.ws.dto.SequencerRunDto;
import ca.on.oicr.silentlake.ws.dto.SequencerSampleDto;

import com.google.common.collect.Lists;

@Stateless
@Path("sequencerruns")
public class SequencerRunsResource {

   @EJB
   SequencerRunService sequencerRunService;

   @EJB
   SampleService sampleService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<SequencerRunDto> getSequencerRuns() {
      List<SequencerRunDto> result = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("sequencerrun/").build();
      final URI sampleBaseUri = uriInfo.getBaseUriBuilder().path("sample/").build();

      List<SequencerRun> sequencerRuns = sequencerRunService.getSequencerRuns();
      for (SequencerRun sequencerRun : sequencerRuns) {
         SequencerRunDto sequencerRunDto = Dtos.asDto(sequencerRun);// This sets up the sequencerRunDto and the laneDtos except for the
                                                                    // samples collection of the laneDtos

         if (sequencerRunDto.getPositions() != null) {
            for (LaneDto laneDto : sequencerRunDto.getPositions()) {
               Lane correspondingLane = laneLookupByIndex(laneDto.getPosition(), sequencerRun.getLaneCollection());
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
         sequencerRunDto.setId(sequencerRunService.getSequencerRunId(sequencerRun));
         sequencerRunDto.setUrl(baseUri.toString() + sequencerRunDto.getId());
         result.add(sequencerRunDto);
      }

      return result;
   }

   // Not sure if this is the proper place for this method
   private Lane laneLookupByIndex(Integer laneIndex, Collection<Lane> laneCollection) {
      for (Lane lane : laneCollection) {
         if (lane.getLaneIndex() == laneIndex) {
            return lane;
         }
      }
      return null; // Should never happen
   }

}

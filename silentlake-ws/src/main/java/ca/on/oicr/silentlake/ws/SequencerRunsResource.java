package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.SequencerRun;

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
         SequencerRunDto sequencerRunDto = Dtos.asDto(sequencerRun);

         for (LaneDto positionDto : sequencerRunDto.getPositions()) {
            if (positionDto.getSamples() != null) {
               for (SequencerSampleDto sequencerSampleDto : positionDto.getSamples()) {
                  sequencerSampleDto.setSampleUrl(sampleBaseUri.toString() + sequencerSampleDto.getSampleId());
               }
            }
         }
         sequencerRunDto.setId(sequencerRun.getSequencerRunId());
         sequencerRunDto.setUrl(baseUri.toString() + sequencerRunDto.getId());
         result.add(sequencerRunDto);
      }

      return result;
   }

}

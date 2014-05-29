package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.SequencerRun;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.SequencerRunService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.LaneDto;
import ca.on.oicr.silentlake.ws.dto.SequencerRunDto;
import ca.on.oicr.silentlake.ws.dto.SequencerSampleDto;

@Stateless
@Path("/sequencerrun")
public class SequencerRunResource {

   @EJB
   private SequencerRunService sequencerRunService;

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
         for (LaneDto positionDto : result.getPositions()) {
            if (positionDto.getSamples() != null) {
               for (SequencerSampleDto sequencerSampleDto : positionDto.getSamples()) {
                  sequencerSampleDto.setSampleUrl(sampleBaseUri.toString() + sequencerSampleDto.getSampleId());
               }
            }
         }
         result.setId(id);
         result.setUrl(baseUri.toString() + id);
      }

      return result;
   }
}

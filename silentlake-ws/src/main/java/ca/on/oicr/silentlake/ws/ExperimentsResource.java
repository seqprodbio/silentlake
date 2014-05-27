package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Experiment;

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

import ca.on.oicr.silentlake.service.ExperimentService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.ExperimentDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/experiments")
public class ExperimentsResource {

   @EJB
   private ExperimentService experimentService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<ExperimentDto> getExperiments() {
      List<ExperimentDto> experiments = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("experiment/").build();
      final URI userBaseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      final URI studyBaseUri = uriInfo.getBaseUriBuilder().path("study/").build();
      final URI experimentLibraryDesignUri = uriInfo.getBaseUriBuilder().path("experimentlibrarydesign/").build();
      final URI experimentSpotDesignUri = uriInfo.getBaseUriBuilder().path("experimentspotdesign/").build();

      for (Experiment experiment : experimentService.getExperiments()) {
         ExperimentDto experimentDto = Dtos.asDto(experiment);
         experimentDto.setId(experiment.getExperimentId()); // In this case, it returns the id but this may not always be the case (it may
                                                            // return sw_accession)
         experimentDto.setUrl(baseUri.toString() + experiment.getExperimentId());
         experimentDto.setExperimentLibraryDesignUrl(experimentLibraryDesignUri.toString() + experimentDto.getExperimentLibraryDesignId());
         experimentDto.setExperimentSpotDesignUrl(experimentSpotDesignUri.toString() + experimentDto.getExperimentSpotDesignId());
         experimentDto.setUserUrl(userBaseUri.toString() + experimentDto.getUserId());
         experimentDto.setStudyUrl(studyBaseUri.toString() + experimentDto.getStudyId());

         experiments.add(experimentDto);
      }
      return experiments;
   }
}

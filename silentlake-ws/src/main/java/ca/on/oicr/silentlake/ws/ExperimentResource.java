package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Experiment;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.ExperimentService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.ExperimentDto;

@Stateless
@Path("/experiment")
public class ExperimentResource {

   @EJB
   private ExperimentService experimentService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public ExperimentDto getExperiment(@PathParam("id") Integer id) {
      ExperimentDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("experiment/").build();
      final URI userBaseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      final URI studyBaseUri = uriInfo.getBaseUriBuilder().path("study/").build();
      final URI experimentLibraryDesignUri = uriInfo.getBaseUriBuilder().path("experimentlibrarydesign/").build();
      final URI experimentSpotDesignUri = uriInfo.getBaseUriBuilder().path("experimentspotdesign/").build();

      Experiment experiment = experimentService.getExperiment(id);
      if (experiment != null) {
         result = Dtos.asDto(experiment);
         result.setExperimentLibraryDesignUrl(experimentLibraryDesignUri.toString() + result.getExperimentLibraryDesignId());
         result.setExperimentSpotDesignUrl(experimentSpotDesignUri.toString() + result.getExperimentSpotDesignId());
         result.setUserUrl(userBaseUri.toString() + result.getUserId());
         result.setStudyUrl(studyBaseUri.toString() + result.getStudyId());
         result.setId(id);
         result.setUrl(baseUri.toString() + id);
      }

      return result;
   }

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(ExperimentDto experimentDto) {
      Experiment experiment = experimentService.fromDto(experimentDto);
      System.out.println(experimentService.create(experiment));
   }

   @DELETE
   @Path("/{id}")
   public void removeExperiment(@PathParam("id") Integer id) {
      experimentService.deleteExperiment(id);
   }
}

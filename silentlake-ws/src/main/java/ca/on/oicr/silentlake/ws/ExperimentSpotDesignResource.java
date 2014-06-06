package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.ExperimentSpotDesign;

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

import ca.on.oicr.silentlake.service.ExperimentSpotDesignService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.ExperimentSpotDesignDto;

@Stateless
@Path("/experimentspotdesign")
public class ExperimentSpotDesignResource {

   @EJB
   private ExperimentSpotDesignService experimentSpotDesignService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public ExperimentSpotDesignDto getExperimentSpotDesign(@PathParam("id") Integer id) {
      ExperimentSpotDesignDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("experimentspotdesign/").build();

      ExperimentSpotDesign experimentSpotDesign = experimentSpotDesignService.getExperimentSpotDesign(id);
      if (experimentSpotDesign != null) {
         result = Dtos.asDto(experimentSpotDesign);
         result.setId(experimentSpotDesign.getExperimentSpotDesignId());
         result.setUrl(baseUri.toString() + experimentSpotDesign.getExperimentSpotDesignId());
      }

      return result;
   }

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public void addExperimentSpotDesign(ExperimentSpotDesignDto experimentSpotDesignDto) {
      if (experimentSpotDesignService.hasValidFields(experimentSpotDesignDto)) {
         ExperimentSpotDesign experimentSpotDesign = Dtos.fromDto(experimentSpotDesignDto);
         if (!experimentSpotDesignService.doesExperimentSpotDesignExistAlready(experimentSpotDesign)) {
            Integer id = experimentSpotDesignService.create(experimentSpotDesign);
            System.out.println(id);
         }
      }
   }

   @DELETE
   @Path("/{id}")
   public void removeExperimentSpotDesign(@PathParam("id") Integer id) {
      experimentSpotDesignService.deleteExperimentSpotDesign(id);
   }

}

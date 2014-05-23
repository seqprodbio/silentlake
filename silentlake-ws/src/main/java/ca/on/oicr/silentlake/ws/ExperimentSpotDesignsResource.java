package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.ExperimentSpotDesign;

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

import ca.on.oicr.silentlake.service.ExperimentSpotDesignService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.ExperimentSpotDesignDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/experimentspotdesigns")
public class ExperimentSpotDesignsResource {

   @EJB
   private ExperimentSpotDesignService experimentSpotDesignService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<ExperimentSpotDesignDto> getExperimentSpotDesigns() {
      List<ExperimentSpotDesignDto> experimentSpotDesigns = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("experimentspotdesign/").build();

      for (ExperimentSpotDesign experimentSpotDesign : experimentSpotDesignService.getExperimentSpotDesigns()) {
         ExperimentSpotDesignDto experimentSpotDesignDto = Dtos.asDto(experimentSpotDesign);
         experimentSpotDesignDto.setId(experimentSpotDesign.getExperimentSpotDesignId());
         experimentSpotDesignDto.setUrl(baseUri.toString() + experimentSpotDesign.getExperimentSpotDesignId());
         experimentSpotDesigns.add(experimentSpotDesignDto);
      }

      return experimentSpotDesigns;
   }
}

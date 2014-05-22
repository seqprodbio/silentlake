package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.ExperimentLibraryDesign;

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

import ca.on.oicr.silentlake.service.ExperimentLibraryDesignService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.ExperimentLibraryDesignDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/experimentlibrarydesigns")
public class ExperimentLibraryDesignsResource {

   @EJB
   private ExperimentLibraryDesignService experimentLibraryDesignService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<ExperimentLibraryDesignDto> getExperimentLibraryDesigns() {
      List<ExperimentLibraryDesignDto> experimentLibraryDesigns = Lists.newArrayList();
      final URI baseURI = uriInfo.getBaseUriBuilder().path("experimentlibrarydesign/").build();

      for (ExperimentLibraryDesign experimentLibraryDesign : experimentLibraryDesignService.getExperimentLibraryDesigns()) {
         ExperimentLibraryDesignDto experimentLibraryDesignDto = Dtos.asDto(experimentLibraryDesign);
         experimentLibraryDesignDto.setId(experimentLibraryDesign.getExperimentLibraryDesignId());
         experimentLibraryDesignDto.setUrl(baseURI.toString() + experimentLibraryDesign.getExperimentLibraryDesignId());
         experimentLibraryDesigns.add(experimentLibraryDesignDto);
      }
      return experimentLibraryDesigns;
   }
}

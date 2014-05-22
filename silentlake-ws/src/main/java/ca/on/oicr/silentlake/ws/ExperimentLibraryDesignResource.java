package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.ExperimentLibraryDesign;
import io.seqware.webservice.generated.model.LibrarySelection;
import io.seqware.webservice.generated.model.LibrarySource;
import io.seqware.webservice.generated.model.LibraryStrategy;

import java.net.URI;
import java.util.List;

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

import ca.on.oicr.silentlake.service.ExperimentLibraryDesignService;
import ca.on.oicr.silentlake.service.LibrarySelectionService;
import ca.on.oicr.silentlake.service.LibrarySourceService;
import ca.on.oicr.silentlake.service.LibraryStrategyService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.ExperimentLibraryDesignDto;

@Stateless
@Path("/experimentlibrarydesign")
public class ExperimentLibraryDesignResource {

   @EJB
   private LibraryStrategyService libraryStrategyService;

   @EJB
   private LibrarySourceService librarySourceService;

   @EJB
   private LibrarySelectionService librarySelectionService;

   @EJB
   private ExperimentLibraryDesignService experimentLibraryDesignService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public ExperimentLibraryDesignDto getExperimentLibraryDesign(@PathParam("id") Integer id) {
      ExperimentLibraryDesignDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("experimentlibrarydesign/").build();

      ExperimentLibraryDesign experimentLibraryDesign = experimentLibraryDesignService.getExperimentLibraryDesign(id);
      if (experimentLibraryDesign != null) {
         result = Dtos.asDto(experimentLibraryDesign);
         result.setId(id);
         result.setUrl(baseUri.toString() + id);
      }

      return result;
   }

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(ExperimentLibraryDesignDto experimentLibraryDesignDto) {
      List<LibraryStrategy> libraryStrategies = libraryStrategyService.getLibraryStrategyByName(experimentLibraryDesignDto.getStrategy());
      List<LibrarySource> librarySources = librarySourceService.getLibrarySourceByName(experimentLibraryDesignDto.getSource());
      List<LibrarySelection> librarySelections = librarySelectionService.getLibrarySelectionByName(experimentLibraryDesignDto
            .getSelection());

      if (libraryStrategies.size() != 1) {
         throw new RuntimeException("More than one or no library strategy found");
      }
      if (librarySources.size() != 1) {
         throw new RuntimeException("More than one or no library source found");
      }
      if (librarySelections.size() != 1) {
         throw new RuntimeException("More than one or no library selection found");
      }

      ExperimentLibraryDesign experimentLibraryDesign = fromDto(experimentLibraryDesignDto, libraryStrategies.get(0),
            librarySources.get(0), librarySelections.get(0));
      Integer id = experimentLibraryDesignService.create(experimentLibraryDesign);
      System.out.println(id);
   }

   @DELETE
   @Path("/{id}")
   public void deleteExperimentLibraryDesign(@PathParam("id") Integer id) {
      experimentLibraryDesignService.deleteExperimentLibraryDesign(id);
   }

   public static ExperimentLibraryDesign fromDto(ExperimentLibraryDesignDto dto, LibraryStrategy strategy, LibrarySource source,
         LibrarySelection selection) {
      ExperimentLibraryDesign to = new ExperimentLibraryDesign();
      to.setStrategy(strategy);
      to.setSource(source);
      to.setSelection(selection);
      if (dto.getName() != null) {
         to.setName(dto.getName());
      }
      return to;
   }
}

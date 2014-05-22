package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.ExperimentLibraryDesign;
import io.seqware.webservice.generated.model.LibrarySelection;
import io.seqware.webservice.generated.model.LibrarySource;
import io.seqware.webservice.generated.model.LibraryStrategy;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import ca.on.oicr.silentlake.service.ExperimentLibraryDesignService;
import ca.on.oicr.silentlake.service.LibrarySelectionService;
import ca.on.oicr.silentlake.service.LibrarySourceService;
import ca.on.oicr.silentlake.service.LibraryStrategyService;
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

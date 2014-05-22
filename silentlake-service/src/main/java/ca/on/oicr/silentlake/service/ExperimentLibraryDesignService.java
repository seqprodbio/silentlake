package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.ExperimentLibraryDesign;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ExperimentLibraryDesignService {

   public Integer create(ExperimentLibraryDesign experimentLibraryDesign);

   public ExperimentLibraryDesign getExperimentLibraryDesign(Integer id);

   void deleteExperimentLibraryDesign(Integer id);

   public List<ExperimentLibraryDesign> getExperimentLibraryDesigns();

}

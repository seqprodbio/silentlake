package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.ExperimentLibraryDesign;

import javax.ejb.Local;

@Local
public interface ExperimentLibraryDesignService {

   public Integer create(ExperimentLibraryDesign experimentLibraryDesign);

   public ExperimentLibraryDesign getExperimentLibraryDesign(Integer id);

}

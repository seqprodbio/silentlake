package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.ExperimentSpotDesign;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ExperimentSpotDesignService {

   public ExperimentSpotDesign getExperimentSpotDesign(Integer id);

   public Integer create(ExperimentSpotDesign experimentSpotDesign);

   public void deleteExperimentSpotDesign(Integer id);

   public List<ExperimentSpotDesign> getExperimentSpotDesigns();

}

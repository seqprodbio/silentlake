package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Experiment;

import javax.ejb.Local;

import ca.on.oicr.silentlake.ws.dto.ExperimentDto;

@Local
public interface ExperimentService {

   public Experiment getExperiment(Integer id);

   public Experiment fromDto(ExperimentDto from);

   public Integer create(Experiment experiment);

   public void deleteExperiment(Integer id);
}

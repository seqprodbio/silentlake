package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.SequencerRun;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SequencerRunService {

   public SequencerRun findSequencerRun(Integer id);

   public List<SequencerRun> getSequencerRuns();
}

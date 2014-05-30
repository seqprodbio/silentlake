package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.SequencerRun;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SequencerRunDao {

   public SequencerRun findSequencerRunById(Integer id);

   public List<SequencerRun> getSequencerRuns();

   public Integer getIdFromSwAccession(Integer swAccession);
}

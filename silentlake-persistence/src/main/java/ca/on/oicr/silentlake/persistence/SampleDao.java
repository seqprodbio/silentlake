package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.Sample;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SampleDao {

   public Sample getSample(Integer id);

   public List<Sample> getSamples(String idKey);

   public Integer getIdFromAccession(Integer swAccession);
}

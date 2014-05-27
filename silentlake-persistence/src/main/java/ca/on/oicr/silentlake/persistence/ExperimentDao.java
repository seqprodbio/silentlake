package ca.on.oicr.silentlake.persistence;

import javax.ejb.Local;

@Local
public interface ExperimentDao {

   public Integer getIdFromAccession(Integer swAccession);

}

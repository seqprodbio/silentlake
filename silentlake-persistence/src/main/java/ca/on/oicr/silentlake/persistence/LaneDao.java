package ca.on.oicr.silentlake.persistence;

import javax.ejb.Local;

@Local
public interface LaneDao {

   public Integer getIdFromSwAccession(Integer swAccession);

}

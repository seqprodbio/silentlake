package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.Study;

import java.util.List;

import javax.ejb.Local;

@Local
public interface StudyDao {

   public Study getStudy(Integer id);

   public List<Study> getStudies();

   public Integer create(Study study);

}

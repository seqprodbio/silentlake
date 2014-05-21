package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Study;
import io.seqware.webservice.generated.model.StudyType;

import java.util.List;

import javax.ejb.Local;

@Local
public interface StudyService {

   public List<Study> getStudies();

   public Study getStudy(Integer id);

   public StudyType getStudyType(Integer id);

   public void create(Study study, Integer id);

   public void update(Study study);

}

package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Study;

import java.util.List;

import javax.ejb.Local;

@Local
public interface StudyService {

   public List<Study> getStudies();

}

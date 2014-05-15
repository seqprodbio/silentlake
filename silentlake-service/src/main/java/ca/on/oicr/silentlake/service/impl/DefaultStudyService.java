package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomStudyAttributeFacadeREST;
import io.seqware.webservice.controller.CustomStudyFacadeREST;
import io.seqware.webservice.generated.model.Study;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.service.StudyService;

@Stateless
public class DefaultStudyService implements StudyService {

   @EJB
   private CustomStudyFacadeREST studyDao;

   @EJB
   private CustomStudyAttributeFacadeREST studyAttributeDao;

   @Override
   public List<Study> getStudies() {
      return studyDao.findAll();
   }

}

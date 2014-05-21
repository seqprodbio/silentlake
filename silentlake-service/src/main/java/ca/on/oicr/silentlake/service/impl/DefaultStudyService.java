package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomStudyAttributeFacadeREST;
import io.seqware.webservice.controller.CustomStudyFacadeREST;
import io.seqware.webservice.controller.CustomStudyTypeFacadeREST;
import io.seqware.webservice.generated.model.Study;
import io.seqware.webservice.generated.model.StudyAttribute;
import io.seqware.webservice.generated.model.StudyType;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ca.on.oicr.silentlake.persistence.StudyDao;
import ca.on.oicr.silentlake.service.StudyService;

import com.google.common.collect.Lists;

@Stateless
public class DefaultStudyService implements StudyService {

   @EJB
   private CustomStudyFacadeREST studyFacadeRest;

   @EJB
   private CustomStudyTypeFacadeREST studyTypeFacadeRest;

   @EJB
   private StudyDao studyDao;

   @EJB
   private CustomStudyAttributeFacadeREST studyAttributeFacadeRest;

   @Override
   public List<Study> getStudies() {
      return studyDao.getStudies();
   }

   @Override
   public StudyType getStudyType(Integer id) {
      return studyTypeFacadeRest.find(id);
   }

   @Override
   public Study getStudy(Integer id) {
      return studyDao.getStudy(id);
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   @Override
   public void create(Study study, Integer id) {
      study.setCreateTstmp(new Date());
      study.setUpdateTstmp(new Timestamp(new Date().getTime()));
      Integer newStudyId = studyDao.create(study);

      // Must retrieve the newly created study in order to have a copy with a
      // correctly set study_id. This doesn't seem right, but it works.
      Study newStudy = studyFacadeRest.find(newStudyId);

      // Set the attribute with the LIMS id.
      StudyAttribute studyAttribute = new StudyAttribute();
      studyAttribute.setTag("geo_lab_group_id");
      studyAttribute.setValue(id.toString());
      studyAttribute.setStudyId(newStudy);
      List<StudyAttribute> studyAttributes = Lists.newArrayList();
      studyAttributes.add(studyAttribute);
      newStudy.setStudyAttributeCollection(studyAttributes);
      studyAttributeFacadeRest.create(studyAttribute);
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   @Override
   public void update(Study study) {
      studyFacadeRest.edit(study);
   }

}

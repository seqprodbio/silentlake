package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomExperimentFacadeREST;
import io.seqware.webservice.controller.CustomExperimentLibraryDesignFacadeREST;
import io.seqware.webservice.controller.CustomExperimentSpotDesignFacadeREST;
import io.seqware.webservice.controller.CustomRegistrationFacadeREST;
import io.seqware.webservice.controller.CustomStudyFacadeREST;
import io.seqware.webservice.generated.model.Experiment;
import io.seqware.webservice.generated.model.Platform;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.persistence.ExperimentDao;
import ca.on.oicr.silentlake.service.ExperimentService;
import ca.on.oicr.silentlake.service.PlatformService;
import ca.on.oicr.silentlake.ws.dto.ExperimentDto;

@Stateless
public class DefaultExperimentService implements ExperimentService {

   @EJB
   private CustomExperimentFacadeREST experimentFacadeRest;

   @EJB
   private CustomExperimentLibraryDesignFacadeREST experimentLibraryStrategyFacadeRest;

   @EJB
   private CustomExperimentSpotDesignFacadeREST experimentSpotDesignFacadeRest;

   @EJB
   private CustomStudyFacadeREST studyFacadeRest;

   @EJB
   private CustomRegistrationFacadeREST registrationFacadeRest;

   @EJB
   private ExperimentDao experimentDao;

   @EJB
   private PlatformService platformService;

   @PersistenceContext
   private EntityManager em;

   @Override
   public Experiment getExperiment(Integer id) {
      return experimentFacadeRest.find(id);
   }

   @Override
   public Experiment fromDto(ExperimentDto from) {
      Experiment experiment = new Experiment();
      experiment.setCreateTstmp(new Date());
      experiment.setUpdateTstmp(new Timestamp(new Date().getTime()));
      if (from.getName() != null) {
         experiment.setName(from.getName());
      }
      if (from.getPlatform() != null) {
         experiment.setPlatformId(getPlatformFromName(from.getPlatform()));
      }
      if (from.getExperimentLibraryDesignId() != null) {
         experiment.setExperimentLibraryDesignId(experimentLibraryStrategyFacadeRest.find(from.getExperimentLibraryDesignId()));
      }
      if (from.getExperimentSpotDesignId() != null) {
         experiment.setExperimentSpotDesignId(experimentSpotDesignFacadeRest.find(from.getExperimentSpotDesignId()));
      }
      if (from.getStudyId() != null) {
         experiment.setStudyId(studyFacadeRest.find(from.getStudyId()));
      }
      if (from.getQualityType() != null) {
         experiment.setQualityType(from.getQualityType());
      }
      if (from.getSequenceSpace() != null) {
         experiment.setSequenceSpace(from.getSequenceSpace());
      }
      if (from.getUserId() != null) {
         experiment.setOwnerId(registrationFacadeRest.find(from.getUserId()));
      }
      return experiment;
   }

   private Platform getPlatformFromName(String name) {
      if (platformService.getPlatformByName(name).size() != 1) {
         throw new RuntimeException("More than one or no platform found");
      }
      return platformService.getPlatformByName(name).get(0);
   }

   @Override
   public Integer create(Experiment experiment) {
      experimentFacadeRest.create(experiment);
      em.flush();
      if (experiment.getExperimentId() != null) {
         return experimentDao.getIdFromAccession(experiment.getExperimentId()); // We need to double check that experiment.getExperimentId()
                                                                                // returns the accession id each time (so far it has)
      } else {
         return 99999;
      }
   }

   @Override
   public void deleteExperiment(Integer id) {
      experimentFacadeRest.remove(id);
   }

   @Override
   public List<Experiment> getExperiments() {
      return experimentFacadeRest.findAll();
   }

}

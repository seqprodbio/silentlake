package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomExperimentLibraryDesignFacadeREST;
import io.seqware.webservice.generated.model.ExperimentLibraryDesign;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.service.ExperimentLibraryDesignService;

@Stateless
public class DefaultExperimentLibraryDesignService implements ExperimentLibraryDesignService {

   @PersistenceContext
   private EntityManager em;

   @EJB
   private CustomExperimentLibraryDesignFacadeREST experimentLibraryDesignFacadeRest;

   @Override
   public Integer create(ExperimentLibraryDesign experimentLibraryDesign) {
      experimentLibraryDesignFacadeRest.create(experimentLibraryDesign);
      em.flush();
      if (experimentLibraryDesign.getExperimentLibraryDesignId() == null) {
         return 99999;
      } else {
         return experimentLibraryDesign.getExperimentLibraryDesignId();
      }
   }

   @Override
   public ExperimentLibraryDesign getExperimentLibraryDesign(Integer id) {
      return experimentLibraryDesignFacadeRest.find(id);
   }

   @Override
   public void deleteExperimentLibraryDesign(Integer id) {
      experimentLibraryDesignFacadeRest.remove(id);
   }

   @Override
   public List<ExperimentLibraryDesign> getExperimentLibraryDesigns() {
      return experimentLibraryDesignFacadeRest.findAll();
   }

}

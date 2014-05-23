package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomExperimentSpotDesignFacadeREST;
import io.seqware.webservice.generated.model.ExperimentSpotDesign;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.service.ExperimentSpotDesignService;

@Stateless
public class DefaultExperimentSpotDesignService implements ExperimentSpotDesignService {

   @PersistenceContext
   EntityManager em;

   @EJB
   private CustomExperimentSpotDesignFacadeREST experimentSpotDesignFacadeRest;

   @Override
   public ExperimentSpotDesign getExperimentSpotDesign(Integer id) {
      return experimentSpotDesignFacadeRest.find(id);
   }

   @Override
   public Integer create(ExperimentSpotDesign experimentSpotDesign) {
      experimentSpotDesignFacadeRest.create(experimentSpotDesign);
      em.flush();
      if (experimentSpotDesign.getExperimentSpotDesignId() != null) {
         return experimentSpotDesign.getExperimentSpotDesignId();
      } else {
         return 99999;
      }
   }

   @Override
   public void deleteExperimentSpotDesign(Integer id) {
      experimentSpotDesignFacadeRest.remove(id);
   }

   @Override
   public List<ExperimentSpotDesign> getExperimentSpotDesigns() {
      return experimentSpotDesignFacadeRest.findAll();
   }

}

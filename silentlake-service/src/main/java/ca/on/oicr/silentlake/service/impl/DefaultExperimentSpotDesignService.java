package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomExperimentSpotDesignFacadeREST;
import io.seqware.webservice.controller.CustomExperimentSpotDesignReadSpecFacadeREST;
import io.seqware.webservice.generated.model.ExperimentSpotDesign;
import io.seqware.webservice.generated.model.ExperimentSpotDesignReadSpec;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.service.ExperimentSpotDesignService;
import ca.on.oicr.silentlake.ws.dto.ExperimentSpotDesignDto;

import com.google.common.collect.Lists;

@Stateless
public class DefaultExperimentSpotDesignService implements ExperimentSpotDesignService {

   @PersistenceContext
   EntityManager em;

   @EJB
   private CustomExperimentSpotDesignFacadeREST experimentSpotDesignFacadeRest;

   @EJB
   private CustomExperimentSpotDesignReadSpecFacadeREST experimentSpotDesignReadSpecFacadeRest;

   @Override
   public ExperimentSpotDesign getExperimentSpotDesign(Integer id) {
      return experimentSpotDesignFacadeRest.find(id);
   }

   @Override
   public Integer create(ExperimentSpotDesign experimentSpotDesign) {
      experimentSpotDesignFacadeRest.create(experimentSpotDesign);
      em.flush();

      List<ExperimentSpotDesignReadSpec> experimentSpotDesignReadSpecs = Lists.newArrayList();

      List<String> readSpecStrings = Lists.newArrayList(experimentSpotDesign.getReadSpec().split("\\{\\.\\.\\}"));
      for (Integer readIndex = 0; readIndex < readSpecStrings.size(); readIndex += 1) {
         ExperimentSpotDesignReadSpec experimentSpotDesignReadSpec = new ExperimentSpotDesignReadSpec();
         experimentSpotDesignReadSpec.setReadIndex(readIndex);
         experimentSpotDesignReadSpec.setExperimentSpotDesignId(experimentSpotDesign);
         experimentSpotDesignReadSpec.setReadClass("Application Read");
         experimentSpotDesignReadSpec.setLength(getLength(readSpecStrings.get(readIndex)));
         if (readSpecStrings.get(readIndex).charAt(1) == 'F') {
            experimentSpotDesignReadSpec.setReadType("Forward");
            experimentSpotDesignReadSpec.setBaseCoord(1);
         } else if (readSpecStrings.get(readIndex).charAt(1) == 'R') {
            experimentSpotDesignReadSpec.setReadType("Reverse");
            experimentSpotDesignReadSpec.setBaseCoord(experimentSpotDesignReadSpec.getLength() + 1);
         }
         experimentSpotDesignReadSpecFacadeRest.create(experimentSpotDesignReadSpec);
         em.flush();
         experimentSpotDesignReadSpecs.add(experimentSpotDesignReadSpec);
      }

      experimentSpotDesign.setExperimentSpotDesignReadSpecCollection(experimentSpotDesignReadSpecs);

      if (experimentSpotDesign.getExperimentSpotDesignId() != null) {
         return experimentSpotDesign.getExperimentSpotDesignId();
      } else {
         return 99999;
      }
   }

   private Integer getLength(String string) {
      String subString = string.substring(3, string.length() - 1);
      return Integer.decode(subString);
   }

   @Override
   public void deleteExperimentSpotDesign(Integer id) {
      if (experimentSpotDesignFacadeRest.find(id).getExperimentSpotDesignReadSpecCollection() != null) {
         for (ExperimentSpotDesignReadSpec experimentSpotDesignReadSpec : experimentSpotDesignFacadeRest.find(id)
               .getExperimentSpotDesignReadSpecCollection()) {
            experimentSpotDesignReadSpecFacadeRest.remove(experimentSpotDesignReadSpec);
         }
      }
      List<ExperimentSpotDesignReadSpec> emptyList = Lists.newArrayList();
      experimentSpotDesignFacadeRest.find(id).setExperimentSpotDesignReadSpecCollection(emptyList);

      experimentSpotDesignFacadeRest.remove(id);
      em.flush();
   }

   @Override
   public List<ExperimentSpotDesign> getExperimentSpotDesigns() {
      return experimentSpotDesignFacadeRest.findAll();
   }

   @Override
   public boolean doesExperimentSpotDesignExistAlready(ExperimentSpotDesign experimentSpotDesign) {
      List<ExperimentSpotDesign> experimentSpotDesigns = getExperimentSpotDesigns();
      if (experimentSpotDesigns != null) {
         for (ExperimentSpotDesign tempExperimentSpotDesign : experimentSpotDesigns) {
            if (tempExperimentSpotDesign.getReadsPerSpot().equals(experimentSpotDesign.getReadsPerSpot())
                  && tempExperimentSpotDesign.getReadSpec().equals(experimentSpotDesign.getReadSpec())) {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public boolean hasValidFields(ExperimentSpotDesignDto experimentSpotDesignDto) {
      if (experimentSpotDesignDto.getReadSpec() != null && experimentSpotDesignDto.getReadsPerSpot() != null) {
         List<String> readSpecs = Lists.newArrayList(experimentSpotDesignDto.getReadSpec().split("\\{\\.\\.\\}"));
         if (readSpecs.size() != experimentSpotDesignDto.getReadsPerSpot()) {
            return false;
         }
         Integer counter = 0;
         while (counter < readSpecs.size()) {
            if (!(readSpecs.get(counter).startsWith("{R*") || readSpecs.get(counter).startsWith("{F*"))
                  || !readSpecs.get(counter).endsWith("}")) {
               return false;
            }
            counter += 1;
         }
         return true;
      }
      return false;
   }

}

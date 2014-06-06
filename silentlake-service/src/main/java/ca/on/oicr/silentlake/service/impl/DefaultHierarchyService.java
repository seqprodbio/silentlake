package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.generated.controller.SampleFacadeREST;
import io.seqware.webservice.generated.model.Sample;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.service.HierarchyService;
import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.ws.dto.SampleHierarchyDto;

import com.google.common.collect.Lists;

@Stateless
public class DefaultHierarchyService implements HierarchyService {

   @EJB
   SampleService sampleService;

   @EJB
   SampleFacadeREST sampleFacadeRest;

   @PersistenceContext
   private EntityManager em;

   @Override
   public void deleteHierarchy() {
      List<Sample> samples = sampleService.getSamples("geo_template_id");
      List<Sample> emptyList = Lists.newArrayList();
      for (Sample sample : samples) {
         sample.setSampleCollection(emptyList);
         sample.setSampleCollection1(emptyList);
         sampleFacadeRest.edit(sample);
         em.flush();
      }
   }

   @Override
   public void createHierarchy(List<SampleHierarchyDto> sampleHierarchyDtos) {
      List<Sample> samples = sampleService.getSamples("geo_template_id");
      for (SampleHierarchyDto sampleHierarchyDto : sampleHierarchyDtos) {
         Sample sample = sampleService.getSampleFromList(sampleHierarchyDto.getSampleId(), samples);
         Sample parentSample = sampleService.getSampleFromList(sampleHierarchyDto.getParentId(), samples);
         if (sample == null) {
            System.out.println("There does not exist a sample with the sample id that was passed in: " + sampleHierarchyDto.getSampleId());
            return; // Return an error code
         }
         Collection<Sample> parentSamples = sample.getSampleCollection1(); // deleteHierarchy must have been called before this so all of
                                                                           // the collections exist
         parentSamples.add(parentSample);
         sample.setSampleCollection1(parentSamples);
         if (parentSample != null) {
            Collection<Sample> childrenSamples = parentSample.getSampleCollection();
            childrenSamples.add(sample);
            parentSample.setSampleCollection(childrenSamples);
            em.merge(parentSample);
            em.flush();
         }
         em.merge(sample);
         em.flush();
      }
   }

   @Override
   public List<SampleHierarchyDto> getHierarchyDtos() {
      List<SampleHierarchyDto> sampleHierarchyDtos = Lists.newArrayList();
      List<Sample> samples = sampleService.getSamples("geo_template_id");
      for (Sample sample : samples) {
         if (sample.getSampleCollection1() != null && !sample.getSampleCollection1().isEmpty()) {
            for (Sample parentSample : sample.getSampleCollection1()) {
               SampleHierarchyDto sampleHierarchyDto = new SampleHierarchyDto();
               sampleHierarchyDto.setParentId(sampleService.getId(parentSample.getSampleAttributeCollection(), "geo_template_id"));
               sampleHierarchyDto.setSampleId(sampleService.getId(sample.getSampleAttributeCollection(), "geo_template_id"));
               sampleHierarchyDtos.add(sampleHierarchyDto);
            }
         } else {
            SampleHierarchyDto sampleHierarchyDto = new SampleHierarchyDto();
            sampleHierarchyDto.setParentId(null);
            sampleHierarchyDto.setSampleId(sampleService.getId(sample.getSampleAttributeCollection(), "geo_template_id"));
            sampleHierarchyDtos.add(sampleHierarchyDto);
         }

      }

      return sampleHierarchyDtos;
   }
}

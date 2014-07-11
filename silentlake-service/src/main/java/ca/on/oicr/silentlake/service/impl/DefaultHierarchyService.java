package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomSampleFacadeREST;
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
   CustomSampleFacadeREST sampleFacadeRest;

   @PersistenceContext
   private EntityManager em;

   // Set all of the parent and children collections to empty collections
   @Override
   public void deleteHierarchy() {
      List<Sample> samples = sampleService.getSamples("geo_template_id");
      List<Sample> emptyList = Lists.newArrayList();
      for (Sample sample : samples) {
         if (sample.getSampleCollection1() == null || sample.getSampleCollection1().isEmpty()
               || sample.getSampleCollection1().iterator().next() == null) {
            sampleFacadeRest.removeNullHierarchy(sample.getSampleId()); // This method checks if there's a null parent id in the table for
                                                                        // this sample so we don't have
                                                                        // to do any further checking here
         }
         sample.setSampleCollection(emptyList);
         sample.setSampleCollection1(emptyList);
         sampleFacadeRest.edit(sample);
         em.flush();
      }
   }

   // In the following function, we create the hierarchy rows that were passed in with a valid parent_id
   // Afterwards, we create hierarchy rows for the rest of the samples with a null parent_id field
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
                                                                           // the collections exist (and are initially empty)
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
      // Create rows in the sample_hierarchy table with null parent_ids for all of the samples that were not specified in the
      // SampleHierarchyDto list provided
      for (Sample sample : samples) {
         if (sample.getSampleCollection1() == null || sample.getSampleCollection1().isEmpty()
               || sample.getSampleCollection1().iterator().next() == null) {
            sampleFacadeRest.createNullHierarchy(sample.getSampleId());
         }
      }
   }

   // Creates a list of SampleHierachyDtos from the sample parent and children collections
   @Override
   public List<SampleHierarchyDto> getHierarchyDtos() {
      List<SampleHierarchyDto> sampleHierarchyDtos = Lists.newArrayList();
      List<Sample> samples = sampleService.getSamples("geo_template_id");
      for (Sample sample : samples) {
         if (sample.getSampleCollection1() != null && !sample.getSampleCollection1().isEmpty()
               && sample.getSampleCollection1().iterator().next() != null) {
            for (Sample parentSample : sample.getSampleCollection1()) { // We assume that if the first parent is not null, none of the other
                                                                        // parents can be null either
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

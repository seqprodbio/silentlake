package ca.on.oicr.silentlake.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.model.SampleHierarchy;
import ca.on.oicr.silentlake.persistence.HierarchyDao;
import ca.on.oicr.silentlake.service.HierarchyService;
import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.ws.dto.SampleHierarchyDto;

@Stateless
public class DefaultHierarchyService implements HierarchyService {

   @EJB
   HierarchyDao hierarchyDao;

   @EJB
   SampleService sampleService;

   @PersistenceContext
   EntityManager em;

   @Override
   public List<SampleHierarchy> getSampleHierarchies() {
      return hierarchyDao.getSampleHierarchies();
   }

   @Override
   public void deleteHierarchy() {
      List<SampleHierarchy> sampleHierarchies = hierarchyDao.getSampleHierarchies();
      for (SampleHierarchy sampleHierarchy : sampleHierarchies) {
         em.remove(em.merge(sampleHierarchy));
      }
   }

   @Override
   public void createHierarchy(Set<SampleHierarchy> sampleHierarchies) {
      for (SampleHierarchy sampleHierarchy : sampleHierarchies) {
         em.persist(sampleHierarchy);
         em.flush();
      }
   }

   @Override
   public Set<SampleHierarchy> fromDtos(Set<SampleHierarchyDto> hierarchy) {
      Set<SampleHierarchy> returnSet = new HashSet<SampleHierarchy>();

      for (SampleHierarchyDto sampleHierarchyDto : hierarchy) {
         returnSet.add(fromDto(sampleHierarchyDto));
      }

      return returnSet;
   }

   @Override
   public SampleHierarchy fromDto(SampleHierarchyDto sampleHierarchyDto) {
      SampleHierarchy sampleHierarchy = new SampleHierarchy();
      sampleHierarchy.setSampleId(sampleService.getSample(sampleHierarchyDto.getSampleId()));
      if (sampleHierarchyDto.getParentId() != null) {
         sampleHierarchy.setParentId(sampleService.getSample(sampleHierarchyDto.getParentId()));
      } else {
         sampleHierarchy.setParentId(null);
      }
      return sampleHierarchy;
   }
}

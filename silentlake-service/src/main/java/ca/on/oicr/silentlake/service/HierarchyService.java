package ca.on.oicr.silentlake.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.on.oicr.silentlake.model.SampleHierarchy;
import ca.on.oicr.silentlake.ws.dto.SampleHierarchyDto;

@Local
public interface HierarchyService {

   public List<SampleHierarchy> getSampleHierarchies();

   public void deleteHierarchy();

   public void createHierarchy(Set<SampleHierarchy> sampleHierarchies);

   public Set<SampleHierarchy> fromDtos(Set<SampleHierarchyDto> hierarchy);

   public SampleHierarchy fromDto(SampleHierarchyDto sampleHierarchyDto);
}

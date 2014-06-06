package ca.on.oicr.silentlake.service;

import java.util.List;

import javax.ejb.Local;

import ca.on.oicr.silentlake.ws.dto.SampleHierarchyDto;

@Local
public interface HierarchyService {

   public void deleteHierarchy();

   public void createHierarchy(List<SampleHierarchyDto> sampleHierarchyDtos);

   public List<SampleHierarchyDto> getHierarchyDtos();
}

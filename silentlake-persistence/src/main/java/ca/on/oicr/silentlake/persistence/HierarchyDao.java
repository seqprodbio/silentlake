package ca.on.oicr.silentlake.persistence;

import java.util.List;

import javax.ejb.Local;

import ca.on.oicr.silentlake.model.SampleHierarchy;

@Local
public interface HierarchyDao {

   public List<SampleHierarchy> getSampleHierarchies();

}

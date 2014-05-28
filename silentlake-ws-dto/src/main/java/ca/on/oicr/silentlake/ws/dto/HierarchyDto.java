package ca.on.oicr.silentlake.ws.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "hierarchy")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class HierarchyDto {

   private Set<SampleHierarchyDto> hierarchy;

   public Set<SampleHierarchyDto> getHierarchy() {
      return hierarchy;
   }

   public void setHierarchy(Set<SampleHierarchyDto> hierarchy) {
      this.hierarchy = hierarchy;
   }

}

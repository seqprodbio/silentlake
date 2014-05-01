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

   private Set<SampleParentLinkDto> hierarchy;

   public Set<SampleParentLinkDto> getHierarchy() {
      return hierarchy;
   }

   public void setHierarchy(Set<SampleParentLinkDto> hierarchy) {
      this.hierarchy = hierarchy;
   }

}

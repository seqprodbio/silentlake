package ca.on.oicr.silentlake.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "hierarchy")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class HierarchyDto {

   private List<SampleHierarchyDto> hierarchy;

   public List<SampleHierarchyDto> getHierarchy() {
      return hierarchy;
   }

   public void setHierarchy(List<SampleHierarchyDto> list) {
      this.hierarchy = list;
   }

}

package ca.on.oicr.silentlake.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/*
 * Used by hierarchy
 */

@XmlRootElement(name = "sample_parent_link")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class SampleHierarchyDto {

   private Integer sampleId;
   private Integer parentId;

   @XmlElement(name = "sample_id")
   public Integer getSampleId() {
      return sampleId;
   }

   public void setSampleId(Integer sampleId) {
      this.sampleId = sampleId;
   }

   @XmlElement(name = "parent_id")
   public Integer getParentId() {
      return parentId;
   }

   public void setParentId(Integer parentId) {
      this.parentId = parentId;
   }

}

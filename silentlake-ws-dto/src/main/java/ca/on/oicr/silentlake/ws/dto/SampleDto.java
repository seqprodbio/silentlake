package ca.on.oicr.silentlake.ws.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "sample")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class SampleDto {

   private String children;
   private String name;
   private String parents;
   private String projectName;
   private String sampleName;
   private String url;
   private Integer id;
   private Set<AttributeDto> attributes;

   public String getChildren() {
      return children;
   }

   public void setChildren(String children) {
      this.children = children;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getParents() {
      return parents;
   }

   public void setParents(String parents) {
      this.parents = parents;
   }

   @XmlElement(name = "project_name")
   public String getProjectName() {
      return projectName;
   }

   public void setProjectName(String projectName) {
      this.projectName = projectName;
   }

   @XmlElement(name = "sample_type")
   public String getSampleName() {
      return sampleName;
   }

   public void setSampleName(String sampleName) {
      this.sampleName = sampleName;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Set<AttributeDto> getAttributes() {
      return attributes;
   }

   public void setAttributes(Set<AttributeDto> attributes) {
      this.attributes = attributes;
   }

}

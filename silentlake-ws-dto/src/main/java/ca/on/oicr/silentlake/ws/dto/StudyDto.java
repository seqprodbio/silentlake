package ca.on.oicr.silentlake.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "study")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class StudyDto {

   private String title;
   private String description;
   private String institution;
   private String institutionProjectName;
   private String ownerUrl;
   private String url;
   private Integer typeId;
   private Integer ownerId;
   private Integer id;

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getInstitution() {
      return institution;
   }

   public void setInstitution(String institution) {
      this.institution = institution;
   }

   @XmlElement(name = "institution_project_name")
   public String getInstitutionProjectName() {
      return institutionProjectName;
   }

   public void setInstitutionProjectName(String institutionProjectName) {
      this.institutionProjectName = institutionProjectName;
   }

   @XmlElement(name = "owner_url")
   public String getOwnerUrl() {
      return ownerUrl;
   }

   public void setOwnerUrl(String ownerUrl) {
      this.ownerUrl = ownerUrl;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @XmlElement(name = "type_id")
   public Integer getTypeId() {
      return typeId;
   }

   public void setTypeId(Integer typeId) {
      this.typeId = typeId;
   }

   @XmlElement(name = "owner_id")
   public Integer getOwnerId() {
      return ownerId;
   }

   public void setOwnerId(Integer ownerId) {
      this.ownerId = ownerId;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

}
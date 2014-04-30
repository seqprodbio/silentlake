package ca.on.oicr.silentlake.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "user")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserDto {

   private String email;
   private String firstName;
   private String institution;
   private String lastName;
   private String url;
   private Integer id;

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @XmlElement(name = "firstname")
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getInstitution() {
      return institution;
   }

   public void setInstitution(String institution) {
      this.institution = institution;
   }

   @XmlElement(name = "lastname")
   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
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
}

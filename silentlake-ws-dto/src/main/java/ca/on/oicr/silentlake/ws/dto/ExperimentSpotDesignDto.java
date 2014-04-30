package ca.on.oicr.silentlake.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "experiment_spot_design")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExperimentSpotDesignDto {

   private String readSpec;
   private String url;
   private Integer readsPerSpot;
   private Integer id;

   @XmlElement(name = "read_spec")
   public String getReadSpec() {
      return readSpec;
   }

   public void setReadSpec(String readSpec) {
      this.readSpec = readSpec;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @XmlElement(name = "reads_per_spot")
   public Integer getReadsPerSpot() {
      return readsPerSpot;
   }

   public void setReadsPerSpot(Integer readsPerSpot) {
      this.readsPerSpot = readsPerSpot;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

}

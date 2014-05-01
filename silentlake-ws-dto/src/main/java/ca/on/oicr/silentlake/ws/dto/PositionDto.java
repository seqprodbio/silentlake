package ca.on.oicr.silentlake.ws.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/*
 * Used by SequencerRunDto
 */

@XmlRootElement(name = "position")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class PositionDto {

   private Integer position;
   private Set<SequencerSampleDto> samples;

   public Integer getPosition() {
      return position;
   }

   public void setPosition(Integer position) {
      this.position = position;
   }

   public Set<SequencerSampleDto> getSamples() {
      return samples;
   }

   public void setSamples(Set<SequencerSampleDto> samples) {
      this.samples = samples;
   }

}

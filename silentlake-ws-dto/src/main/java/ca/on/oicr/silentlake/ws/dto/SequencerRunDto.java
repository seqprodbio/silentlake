package ca.on.oicr.silentlake.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "sequencer_run")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class SequencerRunDto {

   private String state;
   private String name;
   private String instrumentName;
   private String url;
   private Integer id;
   private List<LaneDto> positions;

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @XmlElement(name = "instrument_name")
   public String getInstrumentName() {
      return instrumentName;
   }

   public void setInstrumentName(String instrumentName) {
      this.instrumentName = instrumentName;
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

   public List<LaneDto> getPositions() {
      return positions;
   }

   public void setPositions(List<LaneDto> positions) {
      this.positions = positions;
   }

}

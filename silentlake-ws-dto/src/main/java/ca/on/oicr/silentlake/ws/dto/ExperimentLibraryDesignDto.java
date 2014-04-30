package ca.on.oicr.silentlake.ws.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "experiment_library_design")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExperimentLibraryDesignDto {

   private String name;
   private String strategy;
   private String source;
   private String selection;
   private String url;
   private Integer id;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getStrategy() {
      return strategy;
   }

   public void setStrategy(String strategy) {
      this.strategy = strategy;
   }

   public String getSource() {
      return source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getSelection() {
      return selection;
   }

   public void setSelection(String selection) {
      this.selection = selection;
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

package ca.on.oicr.silentlake.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "experiment")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class ExperimentDto {

   private String studyUrl;
   private String experimentLibraryDesignUrl;
   private String experimentSpotDesignUrl;
   private String platform;
   private String name;
   private String sequencerSpace;
   private String qualityType;
   private String userUrl;
   private String url;
   private Integer studyId;
   private Integer experimentLibraryDesignId;
   private Integer experimentSpotDesignId;
   private Integer userId;
   private Integer id;

   @XmlElement(name = "study_url")
   public String getStudyUrl() {
      return studyUrl;
   }

   public void setStudyUrl(String studyUrl) {
      this.studyUrl = studyUrl;
   }

   @XmlElement(name = "experiment_library_design_url")
   public String getExperimentLibraryDesignUrl() {
      return experimentLibraryDesignUrl;
   }

   public void setExperimentLibraryDesignUrl(String experimentLibraryDesignUrl) {
      this.experimentLibraryDesignUrl = experimentLibraryDesignUrl;
   }

   @XmlElement(name = "experiment_spot_design_url")
   public String getExperimentSpotDesignUrl() {
      return experimentSpotDesignUrl;
   }

   public void setExperimentSpotDesignUrl(String experimentSpotDesignUrl) {
      this.experimentSpotDesignUrl = experimentSpotDesignUrl;
   }

   public String getPlatform() {
      return platform;
   }

   public void setPlatform(String platform) {
      this.platform = platform;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @XmlElement(name = "sequencer_space")
   public String getSequencerSpace() {
      return sequencerSpace;
   }

   public void setSequencerSpace(String sequencerSpace) {
      this.sequencerSpace = sequencerSpace;
   }

   @XmlElement(name = "quality_type")
   public String getQualityType() {
      return qualityType;
   }

   public void setQualityType(String qualityType) {
      this.qualityType = qualityType;
   }

   @XmlElement(name = "user_url")
   public String getUserUrl() {
      return userUrl;
   }

   public void setUserUrl(String userUrl) {
      this.userUrl = userUrl;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @XmlElement(name = "study_id")
   public Integer getStudyId() {
      return studyId;
   }

   public void setStudyId(Integer studyId) {
      this.studyId = studyId;
   }

   @XmlElement(name = "experiment_library_design_id")
   public Integer getExperimentLibraryDesignId() {
      return experimentLibraryDesignId;
   }

   public void setExperimentLibraryDesignId(Integer experimentLibraryDesignId) {
      this.experimentLibraryDesignId = experimentLibraryDesignId;
   }

   @XmlElement(name = "experiment_spot_design_id")
   public Integer getExperimentSpotDesignId() {
      return experimentSpotDesignId;
   }

   public void setExperimentSpotDesignId(Integer experimentSpotDesignId) {
      this.experimentSpotDesignId = experimentSpotDesignId;
   }

   @XmlElement(name = "user_id")
   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

}

package ca.on.oicr.silentlake.ws.dto;

import io.seqware.webservice.generated.model.Experiment;
import io.seqware.webservice.generated.model.ExperimentLibraryDesign;
import io.seqware.webservice.generated.model.ExperimentSpotDesign;
import io.seqware.webservice.generated.model.Ius;
import io.seqware.webservice.generated.model.Lane;
import io.seqware.webservice.generated.model.Registration;
import io.seqware.webservice.generated.model.Sample;
import io.seqware.webservice.generated.model.SampleAttribute;
import io.seqware.webservice.generated.model.SampleRelationship;
import io.seqware.webservice.generated.model.SequencerRun;
import io.seqware.webservice.generated.model.Study;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

public class Dtos {

   public static UserDto asDto(Registration from) {
      UserDto returnDto = new UserDto();

      returnDto.setFirstName(from.getFirstName());
      returnDto.setLastName(from.getLastName());
      returnDto.setEmail(from.getEmail());
      if (from.getInstitution() != null) {
         returnDto.setInstitution(from.getInstitution());
      }

      return returnDto;
   }

   public static StudyDto asDto(Study from) {
      StudyDto returnDto = new StudyDto();

      returnDto.setTitle(from.getTitle());
      returnDto.setInstitution(from.getCenterName());
      returnDto.setInstitutionProjectName(from.getCenterProjectName());
      if (from.getDescription() != null) {
         returnDto.setDescription(from.getDescription());
      }
      returnDto.setTypeId(from.getExistingType().getStudyTypeId());
      return returnDto;
   }

   public static ExperimentLibraryDesignDto asDto(ExperimentLibraryDesign from) {
      ExperimentLibraryDesignDto returnDto = new ExperimentLibraryDesignDto();

      if (from.getName() != null) {
         returnDto.setName(from.getName());
      }
      if (from.getStrategy().getName() != null) {
         returnDto.setStrategy(from.getStrategy().getName());
      }
      if (from.getSource().getName() != null) {
         returnDto.setSource(from.getSource().getName());
      }
      if (from.getSelection().getName() != null) {
         returnDto.setSelection(from.getSelection().getName());
      }
      return returnDto;
   }

   public static ExperimentSpotDesignDto asDto(ExperimentSpotDesign from) {
      ExperimentSpotDesignDto returnDto = new ExperimentSpotDesignDto();

      if (from.getReadsPerSpot() != null) {
         returnDto.setReadsPerSpot(from.getReadsPerSpot());
      }
      if (from.getReadSpec() != null) {
         returnDto.setReadSpec(from.getReadSpec());
      }

      return returnDto;
   }

   public static ExperimentDto asDto(Experiment from) {
      ExperimentDto returnDto = new ExperimentDto();

      if (from.getName() != null) {
         returnDto.setName(from.getName());
      }
      if (from.getSequenceSpace() != null) {
         returnDto.setSequencerSpace(returnDto.getSequencerSpace());
      }
      if (from.getQualityType() != null) {
         returnDto.setQualityType(from.getQualityType());
      }
      if (from.getPlatformId().getName() != null) {
         returnDto.setPlatform(from.getPlatformId().getName());
      }

      return returnDto;
   }

   public static SampleDto asDto(Sample from) {
      SampleDto returnDto = new SampleDto();

      if (from.getSampleAttributeCollection() != null && !from.getSampleAttributeCollection().isEmpty()) {
         returnDto.setAttributes(asAttributeSetDto(from.getSampleAttributeCollection()));
      }
      if (from.getName() != null) {
         returnDto.setName(from.getName());
      }
      // TODO: Find the field in the database containing the data to be held in project_name
      if (from.getType() != null) {
         returnDto.setSampleType(from.getType());
      }
      return returnDto;
   }

   public static Set<AttributeDto> asAttributeSetDto(Collection<SampleAttribute> from) {
      Set<AttributeDto> returnSetDto = Sets.newHashSet();

      for (SampleAttribute attribute : from) {
         returnSetDto.add(asDto(attribute));
      }

      return returnSetDto;
   }

   public static AttributeDto asDto(SampleAttribute from) {
      AttributeDto returnDto = new AttributeDto();

      returnDto.setValue(from.getValue());
      returnDto.setName(from.getTag());

      return returnDto;
   }

   public static SequencerRunDto asDto(SequencerRun from) {
      SequencerRunDto returnDto = new SequencerRunDto();

      if (from.getStatus() != null) {
         returnDto.setState(from.getStatus());
      }
      if (from.getName() != null) {
         returnDto.setName(from.getName());
      }
      if (from.getInstrumentName() != null) {
         returnDto.setInstrumentName(from.getInstrumentName());
      }
      if (from.getLaneCollection() != null && !from.getLaneCollection().isEmpty()) {
         returnDto.setPositions(asPositionSetDto(from.getLaneCollection()));
      }
      return returnDto;
   }

   public static Set<PositionDto> asPositionSetDto(Collection<Lane> from) {
      Set<PositionDto> returnSetDto = Sets.newHashSet();

      for (Lane lane : from) {
         returnSetDto.add(asDto(lane));
      }

      return returnSetDto;
   }

   public static PositionDto asDto(Lane from) {
      PositionDto returnDto = new PositionDto();
      if (from.getLaneIndex() != null) {
         returnDto.setPosition(from.getLaneIndex());
      }
      if (from.getIusCollection() != null && !from.getIusCollection().isEmpty()) {
         returnDto.setSamples(asSequencerSampleSetDto(from.getIusCollection()));
      }
      return returnDto;
   }

   public static Set<SequencerSampleDto> asSequencerSampleSetDto(Collection<Ius> from) {
      Set<SequencerSampleDto> returnSetDto = Sets.newHashSet();

      for (Ius ius : from) {
         returnSetDto.add(asDto(ius));
      }

      return returnSetDto;
   }

   // TODO: Double check with Tony that Ius is the data that SequencerSampleDto is supposed to be representing
   public static SequencerSampleDto asDto(Ius from) {
      SequencerSampleDto returnDto = new SequencerSampleDto();

      if (from.getTag() != null) {
         returnDto.setBarcode(from.getTag());
      }

      return returnDto;
   }

   // This is the class contained within hierarchy
   // SampleRelationship may not be the correct class to use here
   public static SampleParentLinkDto asDto(SampleRelationship from) {
      SampleParentLinkDto returnDto = new SampleParentLinkDto();

      if (from.getChildId() != null) {
         returnDto.setSampleId(from.getChildId().getSampleId());
      }
      if (from.getParentId() != null) {
         returnDto.setParentId(from.getParentId().getSampleId());
      }

      return returnDto;
   }
}
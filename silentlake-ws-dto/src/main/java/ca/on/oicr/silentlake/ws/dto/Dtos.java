package ca.on.oicr.silentlake.ws.dto;

import io.seqware.webservice.generated.model.Experiment;
import io.seqware.webservice.generated.model.ExperimentLibraryDesign;
import io.seqware.webservice.generated.model.ExperimentSpotDesign;
import io.seqware.webservice.generated.model.Ius;
import io.seqware.webservice.generated.model.Lane;
import io.seqware.webservice.generated.model.Registration;
import io.seqware.webservice.generated.model.Sample;
import io.seqware.webservice.generated.model.SampleAttribute;
import io.seqware.webservice.generated.model.SequencerRun;
import io.seqware.webservice.generated.model.Study;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.on.oicr.silentlake.model.SampleHierarchy;

import com.google.common.collect.Lists;
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

   public static Registration fromDto(UserDto dto) {
      Registration registration = new Registration();
      return fromDto(dto, registration);
   }

   public static Registration fromDto(UserDto dto, Registration to) {
      // TODO: check that registration is not null
      to.setFirstName(dto.getFirstName());
      to.setLastName(dto.getLastName());
      to.setEmail(dto.getEmail());
      if (dto.getInstitution() != null) {
         to.setInstitution(dto.getInstitution());
      }
      return to;
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

   public static Study fromDto(StudyDto dto) {
      Study study = new Study();
      return fromDto(dto, study);
   }

   public static Study fromDto(StudyDto dto, Study to) {
      // TODO: check that registration is not null
      to.setTitle(dto.getTitle());
      to.setCenterName(dto.getInstitution());
      to.setCenterProjectName(dto.getInstitutionProjectName());
      if (dto.getDescription() != null) {
         to.setDescription(dto.getDescription());
      }
      return to;
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

   public static ExperimentSpotDesign fromDto(ExperimentSpotDesignDto dto) {
      ExperimentSpotDesign experimentSpotDesign = new ExperimentSpotDesign();
      return fromDto(dto, experimentSpotDesign);
   }

   public static ExperimentSpotDesign fromDto(ExperimentSpotDesignDto dto, ExperimentSpotDesign to) {
      if (dto.getReadSpec() != null) {
         to.setReadSpec(dto.getReadSpec());
      }
      if (dto.getReadsPerSpot() != null) {
         to.setReadsPerSpot(dto.getReadsPerSpot());
      }
      return to;
   }

   public static ExperimentDto asDto(Experiment from) {
      ExperimentDto returnDto = new ExperimentDto();

      if (from.getName() != null) {
         returnDto.setName(from.getName());
      }
      if (from.getSequenceSpace() != null) {
         returnDto.setSequenceSpace(from.getSequenceSpace());
      }
      if (from.getQualityType() != null) {
         returnDto.setQualityType(from.getQualityType());
      }
      if (from.getPlatformId() != null && from.getPlatformId().getName() != null) {
         returnDto.setPlatform(from.getPlatformId().getName());
      }
      if (from.getExperimentLibraryDesignId() != null && from.getExperimentLibraryDesignId().getExperimentLibraryDesignId() != null) {
         returnDto.setExperimentLibraryDesignId(from.getExperimentLibraryDesignId().getExperimentLibraryDesignId());
      }
      if (from.getExperimentSpotDesignId() != null && from.getExperimentSpotDesignId().getExperimentSpotDesignId() != null) {
         returnDto.setExperimentSpotDesignId(from.getExperimentSpotDesignId().getExperimentSpotDesignId());
      }
      if (from.getOwnerId() != null && from.getOwnerId().getRegistrationId() != null) {
         returnDto.setUserId(from.getOwnerId().getRegistrationId());
      }
      if (from.getStudyId() != null && from.getStudyId().getStudyId() != null) {
         returnDto.setStudyId(from.getStudyId().getStudyId());
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

   public static List<LaneDto> asPositionSetDto(Collection<Lane> from) {
      List<LaneDto> returnSetDto = Lists.newArrayList();

      for (Lane lane : from) {
         returnSetDto.add(asDto(lane));
      }

      return returnSetDto;
   }

   public static LaneDto asDto(Lane from) {
      LaneDto returnDto = new LaneDto();

      if (from.getLaneIndex() != null) {
         returnDto.setPosition(from.getLaneIndex());
      }
      return returnDto;
   }

   public static SequencerSampleDto asDto(Ius from) {
      SequencerSampleDto returnDto = new SequencerSampleDto();

      if (from.getTag() != null) {
         returnDto.setBarcode(from.getTag());
      }
      if (from.getSampleId() != null && from.getSampleId().getSampleId() != null) {
         returnDto.setSampleId(from.getSampleId().getSampleId());
      }

      return returnDto;
   }

   public static Set<SampleHierarchyDto> asDto(List<SampleHierarchy> sampleHierarchies) {
      Set<SampleHierarchyDto> returnDtos = new HashSet<SampleHierarchyDto>();

      for (SampleHierarchy sampleHierarchy : sampleHierarchies) {
         returnDtos.add(asDto(sampleHierarchy));
      }
      return returnDtos;
   }

   public static SampleHierarchyDto asDto(SampleHierarchy from) {
      SampleHierarchyDto returnDto = new SampleHierarchyDto();

      if (from.getSampleId() != null) {
         returnDto.setSampleId(from.getSampleId().getSampleId());
      }
      if (from.getParentId() != null) {
         returnDto.setParentId(from.getParentId().getSampleId());
      }

      return returnDto;
   }
}

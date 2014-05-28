package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Sample;
import io.seqware.webservice.generated.model.SampleAttribute;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.on.oicr.silentlake.ws.dto.AttributeDto;
import ca.on.oicr.silentlake.ws.dto.SampleDto;

@Local
public interface SampleService {

   public Sample getSample(Integer id);

   public List<Sample> getSamples(String idKey);

   public Integer getId(Collection<SampleAttribute> attributes, String idKey);

   public Sample fromDto(SampleDto sampleDto);

   public Sample fromDto(SampleDto sampleDto, Sample sample);

   public Collection<SampleAttribute> fromDto(Set<AttributeDto> attributeDtos, Sample sample);

   public SampleAttribute fromDto(AttributeDto attributeDto, Sample sample);

   public Sample setId(Sample sample, Integer id);

   public void create(Sample sample, Integer id, SampleDto sampleDto);

   public void update(Sample sample, SampleDto sampleDto);

   public void setAttributes(SampleDto sampleDto, Sample sample);

   public SampleAttribute findByName(Collection<SampleAttribute> sampleAttributes, String name);

   public void remove(Sample sample);

   public Sample getLibrary(Integer id);

   public List<Sample> getLibraries(String idKey);

}

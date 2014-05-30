package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomSampleAttributeFacadeREST;
import io.seqware.webservice.controller.CustomSampleFacadeREST;
import io.seqware.webservice.generated.model.Sample;
import io.seqware.webservice.generated.model.SampleAttribute;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.persistence.LibraryDao;
import ca.on.oicr.silentlake.persistence.SampleDao;
import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.ws.dto.AttributeDto;
import ca.on.oicr.silentlake.ws.dto.SampleDto;

import com.google.common.collect.Lists;

@Stateless
public class DefaultSampleService implements SampleService {

   @PersistenceContext
   private EntityManager em;

   @EJB
   SampleDao sampleDao;

   @EJB
   LibraryDao libraryDao;

   @EJB
   CustomSampleFacadeREST sampleFacadeRest;

   @EJB
   CustomSampleAttributeFacadeREST sampleAttributeFacadeRest;

   @Override
   public Sample getSample(Integer id) {
      return sampleDao.getSample(id);
   }

   @Override
   public List<Sample> getSamples(String idKey) {
      return sampleDao.getSamples(idKey);
   }

   @Override
   public Integer getId(Collection<SampleAttribute> attributes, String idKey) {
      Integer result = null;

      for (SampleAttribute attribute : attributes) {
         if (attribute.getTag().equals(idKey)) {
            result = Integer.valueOf(attribute.getValue());
         }
      }
      return result;
   }

   @Override
   public Sample fromDto(SampleDto sampleDto) {
      Sample sample = new Sample();
      return fromDto(sampleDto, sample);
   }

   @Override
   public Sample fromDto(SampleDto sampleDto, Sample sample) {
      if (sampleDto.getName() != null) {
         sample.setName(sampleDto.getName());
      }
      if (sampleDto.getSampleType() != null) {
         sample.setType(sampleDto.getSampleType());
      }
      return sample;
   }

   @Override
   public List<SampleAttribute> fromDto(Set<AttributeDto> attributeDtos, Sample sample) {
      List<SampleAttribute> result = Lists.newArrayList(); // Not sure if this is the data type we want to use

      if (attributeDtos != null) {
         for (AttributeDto attributeDto : attributeDtos) {
            result.add(fromDto(attributeDto, sample));
         }
      }

      return result;
   }

   @Override
   public SampleAttribute fromDto(AttributeDto attributeDto, Sample sample) {
      SampleAttribute sampleAttribute = new SampleAttribute();
      sampleAttribute.setSampleId(sample);
      sampleAttribute.setTag(attributeDto.getName());
      sampleAttribute.setValue(attributeDto.getValue());
      return sampleAttribute;
   }

   @Override
   public Sample setId(Sample sample, Integer id) {
      Collection<SampleAttribute> sampleAttributes = sample.getSampleAttributeCollection();
      SampleAttribute idAttribute = new SampleAttribute();
      idAttribute.setTag("geo_template_id");
      idAttribute.setValue(id.toString());
      idAttribute.setSampleId(sample);
      sampleAttributes.add(idAttribute);
      sampleAttributeFacadeRest.create(idAttribute);
      sample.setSampleAttributeCollection(sampleAttributes);
      return sample;
   }

   @Override
   public void create(Sample sample, Integer id, SampleDto sampleDto) {
      sample.setCreateTstmp(new Date());
      sample.setUpdateTstmp(new Timestamp(new Date().getTime()));
      sampleFacadeRest.create(sample);
      em.flush();

      Integer newSampleId = sampleDao.getIdFromAccession(sample.getSampleId()); // Works for libraries too

      Sample newSample = sampleFacadeRest.find(newSampleId);
      setAttributes(sampleDto, newSample); // The SampleAttribute class has a field sampleId which needs a Sample so they have to
                                           // set/created after the Sample is created
      newSample = setId(newSample, id);
   }

   @Override
   public void update(Sample sample, SampleDto sampleDto) {
      Collection<SampleAttribute> sampleAttributes = sample.getSampleAttributeCollection();
      for (AttributeDto attributeDto : sampleDto.getAttributes()) {
         SampleAttribute oldAttribute = findByName(sampleAttributes, attributeDto.getName());
         if (oldAttribute != null) {
            sampleAttributes.remove(oldAttribute);
         }
         SampleAttribute newAttribute = fromDto(attributeDto, sample);
         if (oldAttribute == null) {
            sampleAttributeFacadeRest.create(newAttribute);
         } else {
            newAttribute.setSampleAttributeId(oldAttribute.getSampleAttributeId());
            sampleAttributeFacadeRest.edit(newAttribute);
         }
         sampleAttributes.add(newAttribute);
      }
      sample.setSampleAttributeCollection(sampleAttributes);

      sampleFacadeRest.edit(sample);
   }

   @Override
   public void setAttributes(SampleDto sampleDto, Sample sample) {
      sample.setSampleAttributeCollection(fromDto(sampleDto.getAttributes(), sample));
      for (SampleAttribute sampleAttribute : sample.getSampleAttributeCollection()) {
         sampleAttributeFacadeRest.create(sampleAttribute);
      }
   }

   @Override
   public SampleAttribute findByName(Collection<SampleAttribute> sampleAttributes, String name) {
      for (SampleAttribute sampleAttribute : sampleAttributes) {
         if (sampleAttribute.getTag().equals(name)) {
            return sampleAttribute;
         }
      }
      return null;
   }

   @Override
   public void remove(Sample sample) {
      for (SampleAttribute sampleAttribute : sample.getSampleAttributeCollection()) {
         sampleAttributeFacadeRest.remove(sampleAttribute);
      }
      sample.setSampleAttributeCollection(null); // It's possible that the sampleFacadeRest attempts to delete all of the sample attributes
                                                 // as well
      sampleFacadeRest.remove(sample);
   }

   @Override
   public Sample getLibrary(Integer id) {
      return libraryDao.getLibrary(id);
   }

   @Override
   public List<Sample> getLibraries(String idKey) {
      return libraryDao.getLibraries(idKey);
   }

}

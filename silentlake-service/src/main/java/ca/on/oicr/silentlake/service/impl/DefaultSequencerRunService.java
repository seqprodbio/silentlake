package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomIusFacadeREST;
import io.seqware.webservice.controller.CustomLaneFacadeREST;
import io.seqware.webservice.controller.CustomSequencerRunAttributeFacadeREST;
import io.seqware.webservice.controller.CustomSequencerRunFacadeREST;
import io.seqware.webservice.generated.model.Ius;
import io.seqware.webservice.generated.model.Lane;
import io.seqware.webservice.generated.model.SequencerRun;
import io.seqware.webservice.generated.model.SequencerRunAttribute;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.on.oicr.silentlake.persistence.LaneDao;
import ca.on.oicr.silentlake.persistence.SequencerRunDao;
import ca.on.oicr.silentlake.service.SampleService;
import ca.on.oicr.silentlake.service.SequencerRunService;
import ca.on.oicr.silentlake.ws.dto.LaneDto;
import ca.on.oicr.silentlake.ws.dto.SequencerRunDto;
import ca.on.oicr.silentlake.ws.dto.SequencerSampleDto;

import com.google.common.collect.Lists;

@Stateless
public class DefaultSequencerRunService implements SequencerRunService {

   @EJB
   CustomSequencerRunFacadeREST sequencerRunFacadeRest;

   @EJB
   CustomSequencerRunAttributeFacadeREST sequencerRunAttributeFacadeRest;

   @EJB
   CustomIusFacadeREST iusFacadeRest;

   @EJB
   CustomLaneFacadeREST laneFacadeRest;

   @EJB
   SequencerRunDao sequencerRunDao;

   @EJB
   LaneDao laneDao;

   @EJB
   SampleService sampleService;

   @PersistenceContext
   EntityManager em;

   @Override
   public SequencerRun findSequencerRun(Integer id) {
      return sequencerRunDao.findSequencerRunById(id);
   }

   @Override
   public Integer getSequencerRunId(SequencerRun sequencerRun) {
      for (SequencerRunAttribute sequencerRunAttribute : sequencerRun.getSequencerRunAttributeCollection()) {
         if (sequencerRunAttribute.getTag().equals("geo_instrument_run_id")) {
            return Integer.parseInt(sequencerRunAttribute.getValue());
         }
      }
      return null;
   }

   @Override
   public List<SequencerRun> getSequencerRuns() {
      return sequencerRunDao.getSequencerRuns();
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   @Override
   public void create(SequencerRun sequencerRun, SequencerRunDto sequencerRunDto, Integer id) {
      sequencerRun.setCreateTstmp(new Date());
      sequencerRun.setUpdateTstmp(new Timestamp(new Date().getTime()));
      sequencerRunFacadeRest.create(sequencerRun);
      em.flush();
      Integer newId = sequencerRunDao.getIdFromSwAccession(sequencerRun.getSequencerRunId());

      System.out.println(newId);

      SequencerRun newSequencerRun = sequencerRunFacadeRest.find(newId);

      setSequencerRunId(newSequencerRun, id);

      setLanes(newSequencerRun, sequencerRunDto);

   }

   @Override
   public void update(SequencerRun sequencerRun, SequencerRunDto sequencerRunDto) {
      sequencerRunFacadeRest.edit(sequencerRun);
      em.flush();

      sequencerRun.setLaneCollection(updateLanes(sequencerRunDto.getPositions(), sequencerRun));
   }

   @Override
   public void setSequencerRunId(SequencerRun sequencerRun, Integer id) {
      SequencerRunAttribute sequencerRunAttribute = new SequencerRunAttribute();
      sequencerRunAttribute.setSampleId(sequencerRun);
      sequencerRunAttribute.setTag("geo_instrument_run_id");
      sequencerRunAttribute.setValue(id.toString());
      sequencerRunAttributeFacadeRest.create(sequencerRunAttribute);
   }

   @Override
   public void setLanes(SequencerRun sequencerRun, SequencerRunDto sequencerRunDto) {
      List<Lane> lanes = Lists.newArrayList();
      if (sequencerRunDto.getPositions() != null) {
         for (LaneDto laneDto : sequencerRunDto.getPositions()) {
            Lane lane = fromDto(laneDto, sequencerRun);
            lane.setCreateTstmp(new Date());
            lane.setUpdateTstmp(new Timestamp(new Date().getTime()));

            laneFacadeRest.create(lane);
            em.flush();

            Integer newId = laneDao.getIdFromSwAccession(lane.getLaneId());

            Lane newLane = laneFacadeRest.find(newId);

            List<Ius> iuses = Lists.newArrayList();
            for (SequencerSampleDto sequencerSampleDto : laneDto.getSamples()) {
               Ius ius = fromDto(sequencerSampleDto, newLane);
               ius.setCreateTstmp(new Date());
               ius.setUpdateTstmp(new Timestamp(new Date().getTime()));

               iuses.add(ius);
               iusFacadeRest.create(ius);
               em.flush();
            }
            lane.setIusCollection(iuses);
            lanes.add(newLane);
         }
      }
      sequencerRun.setLaneCollection(lanes);
   }

   @Override
   public List<Lane> updateLanes(List<LaneDto> laneDtos, SequencerRun sequencerRun) {
      List<Lane> lanes = Lists.newArrayList();
      if (laneDtos != null) {
         for (LaneDto laneDto : laneDtos) {
            Lane lane = laneLookupByIndex(laneDto.getPosition(), sequencerRun.getLaneCollection());
            if (lane != null) {
               lane = fromDto(laneDto, lane, sequencerRun);
               List<Ius> iuses = Lists.newArrayList();
               for (SequencerSampleDto sequencerSampleDto : laneDto.getSamples()) {
                  Ius ius = IusLookUpByBarcode(sequencerSampleDto.getBarcode(), lane.getIusCollection());
                  if (ius == null) {
                     ius = fromDto(sequencerSampleDto, lane);
                     ius.setCreateTstmp(new Date());
                     ius.setUpdateTstmp(new Timestamp(new Date().getTime()));

                     iusFacadeRest.create(ius);
                     em.flush();
                  } else {
                     ius = fromDto(sequencerSampleDto, ius, lane);
                     iusFacadeRest.edit(ius);
                     em.flush();
                  }
                  iuses.add(ius);
               }
               lane.setIusCollection(iuses);
               laneFacadeRest.edit(lane);
               em.flush();
            } else {
               lane = fromDto(laneDto, sequencerRun);
               lane.setCreateTstmp(new Date());
               lane.setUpdateTstmp(new Timestamp(new Date().getTime()));

               List<Ius> iuses = Lists.newArrayList(); // I assume that if the lane didn't exist before, the IUSes didn't exist either
               for (SequencerSampleDto sequencerSampleDto : laneDto.getSamples()) {
                  Ius ius = fromDto(sequencerSampleDto, lane);
                  ius.setCreateTstmp(new Date());
                  ius.setUpdateTstmp(new Timestamp(new Date().getTime()));

                  iusFacadeRest.create(ius);
                  iuses.add(ius);
                  em.flush();
               }
               laneFacadeRest.create(lane);
               em.flush();
            }
            lanes.add(lane);
         }
      }
      return lanes;
   }

   // Not sure if this is the proper place for this method
   @Override
   public Ius IusLookUpByBarcode(String barcode, Collection<Ius> iusCollection) {
      for (Ius ius : iusCollection) {
         if (ius.getTag().equals(barcode)) {
            return ius;
         }
      }
      return null;
   }

   // Not sure if this is the proper place for this method
   @Override
   public Lane laneLookupByIndex(Integer laneIndex, Collection<Lane> laneCollection) {
      for (Lane lane : laneCollection) {
         if (lane.getLaneIndex() == laneIndex) {
            return lane;
         }
      }
      return null;
   }

   @Override
   public SequencerRun fromDto(SequencerRunDto sequencerRunDto) {
      SequencerRun result = new SequencerRun();

      return fromDto(sequencerRunDto, result);
   }

   @Override
   public SequencerRun fromDto(SequencerRunDto sequencerRunDto, SequencerRun sequencerRun) {

      if (sequencerRunDto.getName() != null) {
         sequencerRun.setName(sequencerRunDto.getName());
      }
      if (sequencerRunDto.getInstrumentName() != null) {
         sequencerRun.setInstrumentName(sequencerRunDto.getInstrumentName());
      }
      if (sequencerRunDto.getState() != null) {
         sequencerRun.setStatus(sequencerRunDto.getState());
      }

      return sequencerRun;
   }

   @Override
   public Lane fromDto(LaneDto laneDto, SequencerRun sequencerRun) {
      Lane result = new Lane();

      return fromDto(laneDto, result, sequencerRun);
   }

   @Override
   public Lane fromDto(LaneDto laneDto, Lane lane, SequencerRun sequencerRun) {
      if (laneDto.getPosition() != null) {
         lane.setLaneIndex(laneDto.getPosition());
      }
      lane.setSequencerRunId(sequencerRun);
      return lane;
   }

   @Override
   public Ius fromDto(SequencerSampleDto sequencerSampleDto, Lane lane) {
      Ius result = new Ius();

      return fromDto(sequencerSampleDto, result, lane);
   }

   @Override
   public Ius fromDto(SequencerSampleDto sequencerSampleDto, Ius ius, Lane lane) {
      if (sequencerSampleDto.getBarcode() != null) {
         ius.setTag(sequencerSampleDto.getBarcode());
      }
      if (sequencerSampleDto.getSampleId() != null && sampleService.getSample(sequencerSampleDto.getSampleId()) != null) {
         ius.setSampleId(sampleService.getSample(sequencerSampleDto.getSampleId()));
      } else if (sequencerSampleDto.getSampleId() != null) { // Meaning the sample wasn't found but the id exists
         // TODO: Return error code since there's no sample with that id
      }
      ius.setLaneId(lane);
      return ius;
   }

}

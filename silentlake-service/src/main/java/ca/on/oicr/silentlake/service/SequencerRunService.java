package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Ius;
import io.seqware.webservice.generated.model.Lane;
import io.seqware.webservice.generated.model.SequencerRun;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import ca.on.oicr.silentlake.ws.dto.LaneDto;
import ca.on.oicr.silentlake.ws.dto.SequencerRunDto;
import ca.on.oicr.silentlake.ws.dto.SequencerSampleDto;

@Local
public interface SequencerRunService {

   public SequencerRun findSequencerRun(Integer id);

   public Integer getSequencerRunId(SequencerRun sequencerRun);

   public List<SequencerRun> getSequencerRuns();

   public void create(SequencerRun sequencerRun, SequencerRunDto sequencerRunDto, Integer id);

   public void update(SequencerRun updatedSequencerRun, SequencerRunDto sequencerRunDto);

   public void setSequencerRunId(SequencerRun sequencerRun, Integer id);

   public void setLanes(SequencerRun sequencerRun, SequencerRunDto sequencerRunDto);

   public List<Lane> updateLanes(List<LaneDto> laneDtos, SequencerRun sequencerRun);

   public Ius IusLookUpByBarcode(String barcode, Collection<Ius> iusCollection);

   public Lane laneLookupByIndex(Integer laneIndex, Collection<Lane> laneCollection);

   public SequencerRun fromDto(SequencerRunDto sequencerRunDto);

   public SequencerRun fromDto(SequencerRunDto sequencerRunDto, SequencerRun sequencerRun);

   public Lane fromDto(LaneDto laneDto, SequencerRun sequencerRun);

   public Lane fromDto(LaneDto laneDto, Lane lane, SequencerRun sequencerRun);

   public Ius fromDto(SequencerSampleDto sequencerSampleDto, Lane lane);

   public Ius fromDto(SequencerSampleDto sequencerSampleDto, Ius ius, Lane lane);
}

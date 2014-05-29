package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomSequencerRunFacadeREST;
import io.seqware.webservice.generated.model.SequencerRun;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.service.SequencerRunService;

@Stateless
public class DefaultSequencerRunService implements SequencerRunService {

   @EJB
   CustomSequencerRunFacadeREST sequencerRunFacadeRest;

   @Override
   public SequencerRun findSequencerRun(Integer id) {
      return sequencerRunFacadeRest.find(id);
   }

   @Override
   public List<SequencerRun> getSequencerRuns() {
      return sequencerRunFacadeRest.findAll();
   }

}

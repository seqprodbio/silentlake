package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.SequencerRun;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.SequencerRunDao;

@Stateless
public class JpaSequencerRunDao implements SequencerRunDao {

   @PersistenceContext
   EntityManager em;

   @Override
   public SequencerRun findSequencerRunById(Integer id) {
      Query query = em
            .createQuery(
                  "SELECT sr FROM SequencerRun sr, IN(sr.sequencerRunAttributeCollection) sra WHERE sra.tag LIKE 'geo_instrument_run_id' AND sra.value LIKE :id")
            .setParameter("id", id.toString());
      return getSingleResultOrNull(query);
   }

   @SuppressWarnings("unchecked")
   public static <T> T getSingleResultOrNull(Query query) {
      query.setMaxResults(1);
      List<?> results = query.getResultList();
      if (results.isEmpty()) {
         return null;
      } else if (results.size() == 1) {
         return (T) results.get(0);
      }
      throw new NonUniqueResultException();
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<SequencerRun> getSequencerRuns() {
      Query query = em
            .createQuery("SELECT sr FROM SequencerRun sr, IN(sr.sequencerRunAttributeCollection) sra WHERE sra.tag LIKE 'geo_instrument_run_id'");
      return query.getResultList();
   }

   @Override
   public Integer getIdFromSwAccession(Integer swAccession) {
      Query query = em.createQuery("SELECT sr.sequencerRunId FROM SequencerRun sr WHERE sr.swAccession = :swAccession").setParameter(
            "swAccession", swAccession);
      return getSingleResultOrNull(query);
   }
}

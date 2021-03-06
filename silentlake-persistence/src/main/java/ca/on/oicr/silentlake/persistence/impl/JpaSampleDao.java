package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.Sample;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.SampleDao;

@Stateless
public class JpaSampleDao implements SampleDao {

   @PersistenceContext
   EntityManager em;

   @Override
   public Sample getSample(Integer id) {
      Query query = em
            .createQuery(
                  "SELECT s FROM Sample s, IN(s.sampleAttributeCollection) sa WHERE ( SELECT COUNT(sa2) FROM s.sampleAttributeCollection sa2 WHERE sa2.tag LIKE 'geo_run_id_and_position_and_slot') = 0 AND sa.tag LIKE 'geo_template_id' and sa.value LIKE :id")
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
   public List<Sample> getSamples(String idKey) {
      Query query = em
            .createQuery("SELECT DISTINCT(s) FROM Sample s WHERE ( SELECT COUNT(sa) FROM s.sampleAttributeCollection sa WHERE sa.tag LIKE 'geo_run_id_and_position_and_slot') = 0");
      return query.getResultList();
   }

   @Override
   public Integer getIdFromAccession(Integer swAccession) { // Works for both samples and libraries
      Query query = em.createQuery("SELECT s.sampleId FROM Sample s WHERE s.swAccession = :swAccession").setParameter("swAccession",
            swAccession);
      return getSingleResultOrNull(query);
   }
}

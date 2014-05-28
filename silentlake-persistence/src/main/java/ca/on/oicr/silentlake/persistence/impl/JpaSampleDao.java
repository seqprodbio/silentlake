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
      Query query = em.createQuery(
            "SELECT s FROM Sample s, IN(s.sampleAttributeCollection) sa WHERE sa.tag LIKE 'geo_template_id' and sa.value LIKE :id")
            .setParameter("id", id.toString()); // Note that this could return a library (need to fix this)
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
      Query query = em.createQuery("SELECT s FROM Sample s"); // This could also return a library
      return query.getResultList();
   }

   @Override
   public Integer getIdFromAccession(Integer swAccession) { // Works for both samples and libraries
      Query query = em.createQuery("SELECT s.sampleId from Sample s WHERE s.swAccession = :swAccession").setParameter("swAccession",
            swAccession);
      return getSingleResultOrNull(query);
   }
}

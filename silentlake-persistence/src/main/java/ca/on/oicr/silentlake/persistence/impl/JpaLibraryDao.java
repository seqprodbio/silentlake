package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.Sample;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.LibraryDao;

@Stateless
public class JpaLibraryDao implements LibraryDao {

   @PersistenceContext
   EntityManager em;

   @Override
   public Sample getLibrary(Integer id) {
      Query query = em.createQuery(
            "SELECT s FROM Sample s, IN(s.sampleAttributeCollection) sa WHERE sa.tag LIKE 'geo_template_id' and sa.value LIKE :id")
            .setParameter("id", id.toString()); // Note that this could return a sample (need to fix this)
      return getSingleResultOrNull(query);
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Sample> getLibraries(String idKey) {
      Query query = em
            .createQuery("SELECT s FROM Sample s, IN(s.sampleAttributeCollection) sa WHERE sa.tag LIKE 'geo_run_id_and_position_and_slot'"); // This
                                                                                                                                             // could
                                                                                                                                             // also
                                                                                                                                             // return
                                                                                                                                             // a
                                                                                                                                             // sample
      return query.getResultList();
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

}

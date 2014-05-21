package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.Study;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.StudyDao;

@Stateless
public class JpaStudyDao implements StudyDao {

   @PersistenceContext
   private EntityManager em;

   @Override
   public Study getStudy(Integer id) {
      Query query = em.createQuery(
            "SELECT s FROM Study s, IN(s.studyAttributeCollection) sa WHERE sa.tag LIKE 'geo_lab_group_id' AND sa.value LIKE :id")
            .setParameter("id", id.toString());
      return getSingleResultOrNull(query);
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Study> getStudies() {
      Query query = em.createQuery("SELECT s FROM Study s, IN(s.studyAttributeCollection) sa WHERE sa.tag LIKE 'geo_lab_group_id'");
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

   @Override
   public Integer create(Study study) {
      em.persist(study);
      em.flush();
      // study.getStudyId() actually gets the swAccession, so we need another query to actually retrieve the id.
      // This really isn't ideal.
      return getIdFromAccession(study.getStudyId());
   }

   // This bit is messy. The only way to get back the actual id.
   private Integer getIdFromAccession(Integer swAccession) {
      Query query = em.createQuery("SELECT s.studyId FROM Study s WHERE s.swAccession = :swAccession").setParameter("swAccession",
            swAccession);
      return getSingleResultOrNull(query);
   }
}

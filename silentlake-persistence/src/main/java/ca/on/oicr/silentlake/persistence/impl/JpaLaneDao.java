package ca.on.oicr.silentlake.persistence.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.LaneDao;

@Stateless
public class JpaLaneDao implements LaneDao {

   @PersistenceContext
   EntityManager em;

   @Override
   public Integer getIdFromSwAccession(Integer swAccession) {
      Query query = em.createQuery("SELECT l.laneId FROM Lane l WHERE l.swAccession = :swAccession").setParameter("swAccession",
            swAccession);
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
}

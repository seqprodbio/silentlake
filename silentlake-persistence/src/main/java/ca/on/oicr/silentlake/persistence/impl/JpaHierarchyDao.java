package ca.on.oicr.silentlake.persistence.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.model.SampleHierarchy;
import ca.on.oicr.silentlake.persistence.HierarchyDao;

@Stateless
public class JpaHierarchyDao implements HierarchyDao {

   @PersistenceContext
   private EntityManager em;

   @SuppressWarnings("unchecked")
   @Override
   public List<SampleHierarchy> getSampleHierarchies() {
      Query query = em.createQuery("SELECT sh FROM SampleHierarchy sh");
      return query.getResultList();
   }

}

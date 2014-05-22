package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.LibraryStrategy;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.LibraryStrategyDao;

@Stateless
public class JpaLibraryStrategyDao implements LibraryStrategyDao {

   @PersistenceContext
   private EntityManager em;

   @SuppressWarnings("unchecked")
   @Override
   public List<LibraryStrategy> getLibraryStrategyByName(String name) {
      Query query = em.createQuery("SELECT ls FROM LibraryStrategy ls WHERE ls.name LIKE :name").setParameter("name", name);

      return query.getResultList();
   }

}

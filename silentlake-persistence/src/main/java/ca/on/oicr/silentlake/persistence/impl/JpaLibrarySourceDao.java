package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.LibrarySource;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.LibrarySourceDao;

@Stateless
public class JpaLibrarySourceDao implements LibrarySourceDao {

   @PersistenceContext
   private EntityManager em;

   @SuppressWarnings("unchecked")
   @Override
   public List<LibrarySource> getLibrarySourceByName(String name) {
      Query query = em.createQuery("SELECT ls FROM LibrarySource ls WHERE ls.name LIKE :name").setParameter("name", name);

      return query.getResultList();
   }

}

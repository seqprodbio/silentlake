package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.LibrarySelection;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.LibrarySelectionDao;

@Stateless
public class JpaLibrarySelectionDao implements LibrarySelectionDao {

   @PersistenceContext
   private EntityManager em;

   @SuppressWarnings("unchecked")
   @Override
   public List<LibrarySelection> getLibrarySelectionByName(String name) {
      Query query = em.createQuery("SELECT ls FROM LibrarySelection ls WHERE ls.name LIKE :name").setParameter("name", name);

      return query.getResultList();
   }
}

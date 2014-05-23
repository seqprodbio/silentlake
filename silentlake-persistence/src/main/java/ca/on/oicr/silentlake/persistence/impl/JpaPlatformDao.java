package ca.on.oicr.silentlake.persistence.impl;

import io.seqware.webservice.generated.model.Platform;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.on.oicr.silentlake.persistence.PlatformDao;

@Stateless
public class JpaPlatformDao implements PlatformDao {

   @PersistenceContext
   private EntityManager em;

   @SuppressWarnings("unchecked")
   @Override
   public List<Platform> getPlatformByName(String name) {
      Query query = em.createQuery("SELECT p FROM Platform p WHERE p.name LIKE :name").setParameter("name", name);

      return query.getResultList();
   }
}

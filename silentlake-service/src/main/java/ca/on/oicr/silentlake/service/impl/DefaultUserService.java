package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomRegistrationFacadeREST;
import io.seqware.webservice.generated.model.Registration;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.service.UserService;

@Stateless
public class DefaultUserService implements UserService {

   // @PersistenceContext(unitName = "io.seqware_seqware-admin-webservice_war_1.0-SNAPSHOTPU")
   // private EntityManager em;

   @EJB
   private CustomRegistrationFacadeREST registrationDao;

   @Override
   public String getName() {
      return "Tony (DeBat)";
   }

   @Override
   public List<Registration> getUsers() {
      return registrationDao.findAll();
   }

}

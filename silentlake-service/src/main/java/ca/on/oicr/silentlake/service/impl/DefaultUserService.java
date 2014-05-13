package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.controller.CustomRegistrationFacadeREST;
import io.seqware.webservice.generated.model.Registration;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.service.UserService;

@Stateless
public class DefaultUserService implements UserService {

   @EJB
   private CustomRegistrationFacadeREST registrationDao;

   @Override
   public List<Registration> getUsers() {
      return registrationDao.findAll();
   }

   @Override
   public void create(Registration registration) {
      registration.setCreateTstmp(new Date());
      registration.setLastUpdateTstmp(new Date());
      registrationDao.create(registration);
   }

   @Override
   public void update(Registration registration) {
      registration.setLastUpdateTstmp(new Date());
      registrationDao.edit(registration);
   }

}

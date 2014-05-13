package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Registration;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserService {

   public List<Registration> getUsers();

   public void create(Registration registration);

   public void update(Registration registration);

}

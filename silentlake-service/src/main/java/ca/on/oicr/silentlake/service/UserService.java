package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Registration;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserService {

   public String getName();

   public List<Registration> getUsers();

   public void create(Registration registration);

}

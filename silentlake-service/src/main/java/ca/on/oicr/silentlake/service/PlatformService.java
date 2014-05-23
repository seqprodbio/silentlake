package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.Platform;

import java.util.List;

import javax.ejb.Local;

@Local
public interface PlatformService {

   public List<Platform> getPlatformByName(String name);

}

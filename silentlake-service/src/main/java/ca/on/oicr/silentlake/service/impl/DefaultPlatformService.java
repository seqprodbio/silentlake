package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.generated.model.Platform;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.persistence.PlatformDao;
import ca.on.oicr.silentlake.service.PlatformService;

@Stateless
public class DefaultPlatformService implements PlatformService {

   @EJB
   private PlatformDao platformDao;

   @Override
   public List<Platform> getPlatformByName(String name) {
      return platformDao.getPlatformByName(name);
   }

}

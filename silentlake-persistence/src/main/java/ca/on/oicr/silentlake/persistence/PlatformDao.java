package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.Platform;

import java.util.List;

import javax.ejb.Local;

@Local
public interface PlatformDao {

   public List<Platform> getPlatformByName(String name);

}

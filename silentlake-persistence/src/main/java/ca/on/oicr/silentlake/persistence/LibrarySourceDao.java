package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.LibrarySource;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibrarySourceDao {

   public List<LibrarySource> getLibrarySourceByName(String name);

}

package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.LibrarySource;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibrarySourceService {

   public List<LibrarySource> getLibrarySourceByName(String name);

}

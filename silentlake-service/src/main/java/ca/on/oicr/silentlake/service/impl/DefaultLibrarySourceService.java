package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.generated.model.LibrarySource;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.persistence.LibrarySourceDao;
import ca.on.oicr.silentlake.service.LibrarySourceService;

@Stateless
public class DefaultLibrarySourceService implements LibrarySourceService {

   @EJB
   private LibrarySourceDao librarySourceDao;

   @Override
   public List<LibrarySource> getLibrarySourceByName(String name) {
      return librarySourceDao.getLibrarySourceByName(name);
   }

   @Override
   public List<String> getLibrarySourceNames() {
      return librarySourceDao.getLibrarySourceNames();
   }

}

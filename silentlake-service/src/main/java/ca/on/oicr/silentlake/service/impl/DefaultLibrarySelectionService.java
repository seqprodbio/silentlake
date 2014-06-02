package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.generated.model.LibrarySelection;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.persistence.LibrarySelectionDao;
import ca.on.oicr.silentlake.service.LibrarySelectionService;

@Stateless
public class DefaultLibrarySelectionService implements LibrarySelectionService {

   @EJB
   private LibrarySelectionDao librarySelectionDao;

   @Override
   public List<LibrarySelection> getLibrarySelectionByName(String name) {
      return librarySelectionDao.getLibrarySelectionByName(name);
   }

   @Override
   public List<String> getLibrarySelectionNames() {
      return librarySelectionDao.getLibrarySelectionNames();
   }

}

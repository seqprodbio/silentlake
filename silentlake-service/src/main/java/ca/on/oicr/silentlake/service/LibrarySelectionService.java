package ca.on.oicr.silentlake.service;

import io.seqware.webservice.generated.model.LibrarySelection;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibrarySelectionService {

   public List<LibrarySelection> getLibrarySelectionByName(String name);

}

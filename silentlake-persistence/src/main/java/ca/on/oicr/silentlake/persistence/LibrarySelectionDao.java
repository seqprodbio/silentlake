package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.LibrarySelection;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibrarySelectionDao {

   public List<LibrarySelection> getLibrarySelectionByName(String name);

}

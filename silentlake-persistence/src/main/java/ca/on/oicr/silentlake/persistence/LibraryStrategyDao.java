package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.LibraryStrategy;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibraryStrategyDao {

   public List<LibraryStrategy> getLibraryStrategyByName(String name);

   public List<String> getLibraryStrategyNames();
}

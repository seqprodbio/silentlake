package ca.on.oicr.silentlake.persistence;

import io.seqware.webservice.generated.model.Sample;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibraryDao {

   public Sample getLibrary(Integer id);

   public List<Sample> getLibraries(String idKey);

}

package ca.on.oicr.silentlake.service.impl;

import io.seqware.webservice.generated.model.LibraryStrategy;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.on.oicr.silentlake.persistence.LibraryStrategyDao;
import ca.on.oicr.silentlake.service.LibraryStrategyService;

@Stateless
public class DefaultLibraryStrategyService implements LibraryStrategyService {

   @EJB
   private LibraryStrategyDao libraryStrategyDao;

   @Override
   public List<LibraryStrategy> getLibraryStrategyByName(String name) {
      return libraryStrategyDao.getLibraryStrategyByName(name);
   }

}

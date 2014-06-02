package ca.on.oicr.silentlake.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.on.oicr.silentlake.service.LibraryStrategyService;

@Stateless
@Path("/librarystrategies")
public class LibraryStrategiesResource {

   @EJB
   LibraryStrategyService libraryStrategyService;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<String> getLibraryStrategyNames() {
      return libraryStrategyService.getLibraryStrategyNames();
   }
}

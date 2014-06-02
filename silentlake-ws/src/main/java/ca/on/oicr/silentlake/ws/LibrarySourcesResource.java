package ca.on.oicr.silentlake.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.on.oicr.silentlake.service.LibrarySourceService;

@Stateless
@Path("/librarysources")
public class LibrarySourcesResource {

   @EJB
   LibrarySourceService librarySourceService;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<String> getLibrarySourceNames() {
      return librarySourceService.getLibrarySourceNames();
   }
}

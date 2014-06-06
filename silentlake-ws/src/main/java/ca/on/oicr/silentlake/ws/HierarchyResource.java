package ca.on.oicr.silentlake.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.on.oicr.silentlake.service.HierarchyService;
import ca.on.oicr.silentlake.ws.dto.HierarchyDto;

@Stateless
@Path("/hierarchy")
public class HierarchyResource {

   @EJB
   HierarchyService hierarchyService;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public HierarchyDto getHierarchy() {
      HierarchyDto hierarchyDto = new HierarchyDto();
      hierarchyDto.setHierarchy(hierarchyService.getHierarchyDtos());
      return hierarchyDto;
   }

   @PUT
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(HierarchyDto hierarchyDto) {
      hierarchyService.deleteHierarchy();
      hierarchyService.createHierarchy(hierarchyDto.getHierarchy());
   }
}

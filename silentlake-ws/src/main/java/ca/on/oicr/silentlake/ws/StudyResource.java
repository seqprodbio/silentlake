package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Study;
import io.seqware.webservice.generated.model.StudyType;

import java.net.URI;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.StudyService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.StudyDto;

@Stateless
@Path("/study")
public class StudyResource {

   @EJB
   private StudyService studyService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public StudyDto getStudy(@PathParam("id") Integer id) {
      StudyDto result = null;
      final URI baseUri = uriInfo.getBaseUriBuilder().path("study/").build();

      Study study = studyService.getStudy(id);
      if (study != null) {
         result = Dtos.asDto(study);
         result.setId(id);
         result.setUrl(baseUri.toString() + id);
      }
      return result;
   }

   @PUT
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public void add(@PathParam("id") Integer id, StudyDto studyDto) {
      Study existingStudy = studyService.getStudy(id);
      StudyType studyType = studyService.getStudyType(studyDto.getTypeId());
      if (existingStudy == null) {
         Study study = Dtos.fromDto(studyDto);
         study.setExistingType(studyType);
         studyService.create(study, id);
      } else {
         Study study = Dtos.fromDto(studyDto, existingStudy);
         study.setExistingType(studyType);
         studyService.update(study);
      }
   }
}

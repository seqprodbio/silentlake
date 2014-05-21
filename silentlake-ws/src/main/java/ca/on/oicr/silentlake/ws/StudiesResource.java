package ca.on.oicr.silentlake.ws;

import io.seqware.webservice.generated.model.Study;
import io.seqware.webservice.generated.model.StudyAttribute;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ca.on.oicr.silentlake.service.StudyService;
import ca.on.oicr.silentlake.ws.dto.Dtos;
import ca.on.oicr.silentlake.ws.dto.StudyDto;

import com.google.common.collect.Lists;

@Stateless
@Path("/studies")
public class StudiesResource {

   public static final String ID_KEY = "geo_lab_group_id";

   @EJB
   private StudyService studyService;

   @Context
   private UriInfo uriInfo;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<StudyDto> getStudies() {
      List<StudyDto> studies = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("study/").build();
      for (Study study : studyService.getStudies()) {
         StudyDto studyDto = Dtos.asDto(study);
         Integer id = getId(study.getStudyAttributeCollection(), ID_KEY);
         studyDto.setId(id);
         studyDto.setUrl(baseUri.toString() + id);
         studies.add(studyDto);
      }
      return studies;
   }

   private Integer getId(Collection<StudyAttribute> attributes, String idKey) {
      Integer result = null;
      for (StudyAttribute attribute : attributes) {
         if (attribute.getTag().equals(idKey)) {
            result = Integer.valueOf(attribute.getValue());
            break;
         }
      }
      return result;
   }
}

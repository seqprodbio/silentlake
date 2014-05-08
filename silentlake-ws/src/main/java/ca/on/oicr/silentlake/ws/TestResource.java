package ca.on.oicr.silentlake.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestResource {

   @GET
   @Path("/foo")
   public List<String> getAll() {
      List<String> list = new ArrayList<String>();
      list.add("one");
      list.add("two");
      list.add("three");
      return list;
   }

   @GET
   @Path("/bar")
   @Produces(MediaType.TEXT_PLAIN)
   public String getAllStuff() {
      return "stuff";
   }
}

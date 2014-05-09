package ca.on.oicr.silentlake.ws;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class SilentlakeApplication extends Application {
   private final Set<Object> singletons = new HashSet<Object>();
   private final Set<Class<?>> empty = new HashSet<Class<?>>();

   public SilentlakeApplication() {
      // ADD YOUR RESTFUL RESOURCES HERE
      // this.singletons.add(new TestResource());
      // this.singletons.add(new UsersResource());
      getClasses().add(TestResource.class);
      getClasses().add(UsersResource.class);
   }

   @Override
   public Set<Class<?>> getClasses() {
      return this.empty;
   }

   @Override
   public Set<Object> getSingletons() {
      return this.singletons;
   }

}

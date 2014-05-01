package ca.on.oicr.silentlake.ws.dto;

import io.seqware.webservice.generated.model.Registration;

public class Dtos {

   public static UserDto asDto(Registration from) {
      UserDto returnNewUserDto = new UserDto();
      return returnNewUserDto;
   }
}

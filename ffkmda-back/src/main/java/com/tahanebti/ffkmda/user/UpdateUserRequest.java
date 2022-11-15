package com.tahanebti.ffkmda.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateUserRequest {

	    
	 

	 @Schema(example = "taha.nebti.2@test.com")
	 @Email
	 private String email;

}

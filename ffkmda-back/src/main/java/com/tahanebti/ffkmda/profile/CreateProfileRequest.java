package com.tahanebti.ffkmda.profile;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfileRequest {
	
	private String firstName;
	private String lastName;
	
	@Past
    private LocalDate birthDate;
	
//	private List<CreatePhoneRequest> phones; 
}


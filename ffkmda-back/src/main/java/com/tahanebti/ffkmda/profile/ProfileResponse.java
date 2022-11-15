package com.tahanebti.ffkmda.profile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
	
	private Long id;
	private String fullName;
	private String firstName;
	private String lastName;
}


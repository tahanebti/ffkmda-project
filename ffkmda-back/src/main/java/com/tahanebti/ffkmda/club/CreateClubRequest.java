package com.tahanebti.ffkmda.club;

import java.time.LocalDate;

import com.tahanebti.ffkmda.profile.CreateProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClubRequest {

	private String nom; 
	private String nom_court;
	private String code;
	private String etat;
	private String type;
	private String logo;
	private String logo_url;
	private String code_region;
}

package com.tahanebti.ffkmda.club;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UpdateClubRequest {

	private String nom; 
	private String nom_court;
	private String code;
	private String etat;
	private String type;
	private String logo;
	private String logo_url;
	private String code_region;
}

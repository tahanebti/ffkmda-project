package com.tahanebti.ffkmda.club;

import java.util.Date;

import com.tahanebti.ffkmda.siege.SiegeResponse;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ClubResponse {
	
	private Long id;
	private String nom; 
	private String nom_court;
	private String code;
	private String etat;
	private String type;
	private String logo;
	private String logo_url;
	private String code_region;
	
	private SiegeResponse siege;
	
//	private Date createdAt;
//	private Date updatedAt;
}

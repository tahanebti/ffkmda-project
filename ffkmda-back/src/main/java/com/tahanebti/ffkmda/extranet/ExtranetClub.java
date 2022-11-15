package com.tahanebti.ffkmda.extranet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tahanebti.ffkmda.siege.Siege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString @Builder 
public class ExtranetClub {

	  private String nom; 
	  private String nom_court;
	  private String code;
	  private String etat;
	  private String type;
	  private String logo;
	  private String logo_url;
	  private String code_region;
	  
	  private ExtranetSiege siege;
	  
	 
}

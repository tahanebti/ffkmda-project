package com.tahanebti.ffkmda.extranet;

import com.tahanebti.ffkmda.club.Club;
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
public class ExtranetSiege {

	//address
	private String num_voie;
	private String type_voie;
	private String nom_voie;
	
	private String code_postal_fr;
	private String code_postal_et;
	private String commune;
	private String code_insee_region;
	private String code_insee_departement;
	private String adresse_complete;
	private String pays_code;
	
	//geo_location
	private String longitude;
	private String latitude;
	
	
	//phone
	private String tel;
	private String tel2;
	private String tel_pro;
	private String mobile;
	private String mobile2;
	private String mobile_pro;
	private String fax;
	private String fax_pro;
	
	//social
	private String web;
	private String twitter;
	private String facebook;
	
	private String batiment;
	private String escalier;	
	private String mail;
}

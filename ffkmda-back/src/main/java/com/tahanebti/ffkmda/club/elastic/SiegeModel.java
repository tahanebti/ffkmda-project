package com.tahanebti.ffkmda.club.elastic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class SiegeModel {

	// social
	private String web;
	private String twitter;
	private String facebook;

	private String batiment;
	private String escalier;
	private String mail;

	// address

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

	// geo_location
	private String longitude;
	private String latitude;

	// phone
	private String tel;
	private String tel2;
	private String tel_pro;
	private String mobile;
	private String mobile2;
	private String mobile_pro;
	private String fax;
	private String fax_pro;
}

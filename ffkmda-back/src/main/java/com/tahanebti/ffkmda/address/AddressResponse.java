package com.tahanebti.ffkmda.address;

import java.util.Date;

import lombok.Data;

@Data 
public class AddressResponse {
	
	
		private Long id;
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
		

}

package com.tahanebti.ffkmda.address;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CreateAddressRequest {

	//@Schema(example = "123 ...")
    //@NotBlank
  	private String num_voie;
  	
	//@Schema(example = "Main Street")
    //@NotBlank
	private String type_voie;
  	
	//@Schema(example = "Seine-saint-denis")
    //@NotBlank
	private String nom_voie;
  	
	@Schema(example = "93000")
    @NotBlank
  	private String code_postal_fr;
  	private String code_postal_et;
  	
  	@Schema(example = "Seine-saint-denis")
    @NotBlank
  	private String commune; 
  	
  	
  	private String code_insee_region;
  	private String code_insee_departement;
  	private String adresse_complete;
  	private String pays_code;
  	
  	//geo_location
  	private String longitude;
  	private String latitude;
}


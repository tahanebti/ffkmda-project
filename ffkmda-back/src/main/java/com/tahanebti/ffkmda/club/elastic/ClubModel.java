package com.tahanebti.ffkmda.club.elastic;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import com.tahanebti.ffkmda.siege.Siege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Document(indexName = "clubs")
public class ClubModel {
	
	@Id
	private Long id;
	private String nom; 
	private String nom_court;
	private String code;
	private String etat;
	private String type;
	private String logo;
	private String logo_url;
	private String code_region;
	
	private SiegeModel siege;
}

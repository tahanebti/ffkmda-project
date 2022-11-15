package com.tahanebti.ffkmda.siege;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tahanebti.ffkmda.address.Address;
import com.tahanebti.ffkmda.base.LongIdEntity;
import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.phone.Phone;
import com.tahanebti.ffkmda.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "sieges")
public class Siege extends LongIdEntity {

	// social
	private String web;
	private String twitter;
	private String facebook;

	private String batiment;
	private String escalier;
	private String mail;

	// address
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

	@OneToOne(mappedBy = "siege", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Club club;
}

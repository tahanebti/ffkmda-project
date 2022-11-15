package com.tahanebti.ffkmda.address;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tahanebti.ffkmda.base.LongIdEntity;
import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.phone.Phone;
import com.tahanebti.ffkmda.phone.PhoneType;
import com.tahanebti.ffkmda.profile.UserProfile;
import com.tahanebti.ffkmda.siege.Siege;
import com.tahanebti.ffkmda.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@ToString
public class Address  extends LongIdEntity {

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
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserProfile profile;
    
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "address")
//   	private Siege siege;
//    
//    public void addSiege(Siege siege) {
//        this.siege = siege;
//        siege.setAddress(this);
//    }
//
//    public void removeSiege() {
//        this.siege.setPhone(null);
//        this.siege = null;
//    }
//    
    
}

package com.tahanebti.ffkmda.phone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tahanebti.ffkmda.base.LongIdEntity;
import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.profile.UserProfile;
import com.tahanebti.ffkmda.siege.Siege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="phones")
public class Phone extends LongIdEntity {
	
	//phone
	private String tel;
	private String tel2;
	private String tel_pro;
	private String mobile;
	private String mobile2;
	private String mobile_pro;
	private String fax;
	private String fax_pro;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private UserProfile profile;
    
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "phone")
//   	private Siege siege;
//    
//    public void addSiege(Siege siege) {
//        this.siege = siege;
//        siege.setPhone(this);
//    }
//
//    public void removeSiege() {
//        this.siege.setPhone(null);
//        this.siege = null;
//    }
}

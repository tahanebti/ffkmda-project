package com.tahanebti.ffkmda.profile;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tahanebti.ffkmda.address.Address;
import com.tahanebti.ffkmda.base.LongIdEntity;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="user_profile")
@ToString(exclude = {"user", "phones"})
public class UserProfile extends LongIdEntity{
	
	private String fullName;
	private String firstName;
	private String lastName;
	
	@Column
    private LocalDate birthDate;
	
    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private User user;

	@OneToMany(mappedBy= "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones; 
	
	@OneToMany(mappedBy= "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses;
	
	@PostConstruct
	public String getFullName() {
	        return getFirstName()
	            .concat(" ")
	            .concat(getLastName());
	 }
}


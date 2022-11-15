package com.tahanebti.ffkmda.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;


import com.tahanebti.ffkmda.base.LongIdEntity;
import com.tahanebti.ffkmda.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "users", "privileges" })
public class Role extends LongIdEntity implements GrantedAuthority{


	@Enumerated(EnumType.STRING)
	@NaturalId
	private RoleName name;

	
	private String authorityContent;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();



	public boolean isAdminRole() {
		return null != this && this.name.equals(RoleName.ROLE_ADMIN);
	}

	@PreRemove
	private void removeRolesFromUsers() {
		for (User user : this.getUsers()) {
			user.getRoles().remove(this);
		}
	}

	@Override
	public String getAuthority() {
		return this.name.name();
	}
	
}

package com.tahanebti.ffkmda.role;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tahanebti.ffkmda.base.BaseRepository;


public interface RoleRepository extends BaseRepository<Role, Long>{

	//Role findByName(String roleName);

	@Query(value = "select roles.* FROM ROLES roles WHERE roles.name = :name", nativeQuery = true)
    Optional<Role> findByName(@Param("name") RoleName roleName);
	
	//List<Role> findByPermissionEntityName(String name, String objectClass, String ext);


	@Query(value = "select roles.* FROM ROLES roles, USERS_ROLES ur WHERE roles.id = ur.role_id and ur.user_id = :id", nativeQuery = true)
    Set<Role> findRolesByUserId(@Param("id") Long id);
}

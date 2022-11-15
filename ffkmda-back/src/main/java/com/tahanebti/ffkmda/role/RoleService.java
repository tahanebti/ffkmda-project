package com.tahanebti.ffkmda.role;

import java.util.Set;

import com.tahanebti.ffkmda.base.BaseService;



public interface RoleService extends BaseService<Role, Long>{
	public Set<Role> getRolesByUserId(Long id);
}


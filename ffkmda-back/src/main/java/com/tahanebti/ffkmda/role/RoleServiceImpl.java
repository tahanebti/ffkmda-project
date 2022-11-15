package com.tahanebti.ffkmda.role;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService{

	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(BaseRepository<Role, Long> baseRepository,
			SpecificationsBuilder<Role> spec, RoleRepository roleRepository) {
		super(baseRepository, spec);
		this.roleRepository = roleRepository;
	}

	@Override
	public Set<Role> getRolesByUserId(Long id) {
        return roleRepository.findRolesByUserId(id);		
	}

	
}


package com.tahanebti.ffkmda.role;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tahanebti.ffkmda.utils.EnumUtil;



@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = EnumUtil.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RoleMapper {

    Role toCreate(CreateRoleRequest request);

    public RoleResponse toResponse(Role user);
    
    void updateFromRequest(UpdateRoleRequest request, @MappingTarget Role role);

}



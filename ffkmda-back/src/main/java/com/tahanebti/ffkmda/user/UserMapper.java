package com.tahanebti.ffkmda.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
//        uses = {RoleService.class, ProfileService.class, OrganizationService.class, TenantService.class, BooleanUtil.class},
 //       injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {
    User toCreate(CreateUserRequest request);
    public UserResponse toResponse(User user);
    void updateFromRequest(UpdateUserRequest request, @MappingTarget User user);
}

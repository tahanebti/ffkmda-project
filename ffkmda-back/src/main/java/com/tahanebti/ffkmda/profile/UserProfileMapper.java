package com.tahanebti.ffkmda.profile;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
      //  uses = PhoneService.class,
      //  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserProfileMapper {

    UserProfile toCreate(CreateProfileRequest request);
    public ProfileResponse toResponse(UserProfile profile);
    void updateFromRequest(UpdateProfileRequest request, @MappingTarget UserProfile profile);

}

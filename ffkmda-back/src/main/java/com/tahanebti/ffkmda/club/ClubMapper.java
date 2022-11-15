package com.tahanebti.ffkmda.club;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tahanebti.ffkmda.profile.CreateProfileRequest;
import com.tahanebti.ffkmda.profile.ProfileResponse;
import com.tahanebti.ffkmda.profile.UpdateProfileRequest;
import com.tahanebti.ffkmda.profile.UserProfile;
import com.tahanebti.ffkmda.siege.SiegeService;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = SiegeService.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ClubMapper {

    Club toCreate(CreateClubRequest request);
    public ClubResponse toResponse(Club club);
    void updateFromRequest(UpdateClubRequest request, @MappingTarget Club club);
}

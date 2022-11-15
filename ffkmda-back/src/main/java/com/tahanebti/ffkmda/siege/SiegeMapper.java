package com.tahanebti.ffkmda.siege;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tahanebti.ffkmda.address.AddressService;
import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.club.ClubResponse;
import com.tahanebti.ffkmda.club.CreateClubRequest;
import com.tahanebti.ffkmda.club.UpdateClubRequest;
import com.tahanebti.ffkmda.phone.PhoneService;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
      //  uses = {PhoneService.class, AddressService.class},
      //  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface SiegeMapper {

	  Siege toCreate(CreateSiegeRequest request);
	    public SiegeResponse toResponse(Siege siege);
	    void updateFromRequest(UpdateSiegeRequest request, @MappingTarget Siege Siege);
}

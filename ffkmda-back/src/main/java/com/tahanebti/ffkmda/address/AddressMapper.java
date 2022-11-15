package com.tahanebti.ffkmda.address;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AddressMapper {

	    Address toCreate(CreateAddressRequest request);

	    
	    AddressResponse toResponse(Address address);

	   
	    void updateFromRequest(UpdateAddressRequest request, @MappingTarget Address address);
}

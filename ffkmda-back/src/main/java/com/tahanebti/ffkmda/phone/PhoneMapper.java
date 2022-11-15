package com.tahanebti.ffkmda.phone;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PhoneMapper {

	   Phone toCreate(CreatePhoneRequest request);
	    public PhoneResponse toResponse(Phone phone);
	    void updateFromRequest(UpdatePhoneRequest request, @MappingTarget Phone phone);
}

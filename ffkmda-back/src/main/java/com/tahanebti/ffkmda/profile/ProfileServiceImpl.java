package com.tahanebti.ffkmda.profile;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

@Service
public class ProfileServiceImpl extends BaseServiceImpl<UserProfile, Long> implements ProfileService{

	private final UserProfileRepository profileRepository;
	private final UserProfileMapper mapper;
	
	public ProfileServiceImpl(BaseRepository<UserProfile, Long> baseRepository, SpecificationsBuilder<UserProfile> spec, 
			UserProfileRepository profileRepository, UserProfileMapper mapper) {
		super(baseRepository, spec);
		this.profileRepository = profileRepository;
		this.mapper = mapper;
	}

	


	@Override
	public UserProfile customProfileCreate(CreateProfileRequest request) {
	    
//		request.getPhones().stream().map(phone -> phone.getNumber())
//        .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
//        .entrySet().stream().filter(m -> m.getValue() > 1).findAny().ifPresent(nPhone -> {
//            throw new PhoneNumberIsRepeatedException(nPhone.getKey());
//        });
	    
		UserProfile profile = mapper.toCreate(request);
        profileRepository.save(profile);
        
		return profile;
	}

}

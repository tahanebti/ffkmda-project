package com.tahanebti.ffkmda.phone;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

@Service
public class PhoneServiceImpl extends BaseServiceImpl<Phone, Long> implements PhoneService{

	private final PhoneRepository phoneRepository;
	
	public PhoneServiceImpl(BaseRepository<Phone, Long> baseRepository, SpecificationsBuilder<Phone> spec,
			PhoneRepository phoneRepository
			) {
		super(baseRepository, spec);
		this.phoneRepository = phoneRepository;
	}

	@Override
	public Set<Phone> getPhonesByUserId(Long id) {
      //  return phoneRepository.findPhonesByUserId(id);		
		return null;
	}

}
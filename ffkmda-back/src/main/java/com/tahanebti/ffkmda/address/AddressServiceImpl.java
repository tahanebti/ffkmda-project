package com.tahanebti.ffkmda.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tahanebti.ffkmda.annotation.Timed;
import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.phone.Phone;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long>
implements AddressService{

	private final AddressRepository addressRepository;
	
	public AddressServiceImpl(BaseRepository<Address, Long> baseRepository, SpecificationsBuilder<Address> spec,
			AddressRepository addressRepository) {
		super(baseRepository, spec);
		this.addressRepository = addressRepository;
	}
	
	
	@Transactional(readOnly = true)
	@Timed @Override
    public Page<Address> getAddressPageableByPredicate(Specification<Address> query, Pageable pageable) {
        return addressRepository
                .findAll(query, pageable);
    }

}

package com.tahanebti.ffkmda.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.tahanebti.ffkmda.base.BaseService;

public interface AddressService extends BaseService<Address, Long> {
	Page<Address> getAddressPageableByPredicate(Specification<Address> query, Pageable pageable);
}

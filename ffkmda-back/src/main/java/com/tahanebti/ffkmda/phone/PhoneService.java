package com.tahanebti.ffkmda.phone;

import java.util.Set;

import com.tahanebti.ffkmda.base.BaseService;

public interface PhoneService extends BaseService<Phone, Long>{

	public Set<Phone> getPhonesByUserId(Long id);
}

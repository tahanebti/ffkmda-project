package com.tahanebti.ffkmda.phone;

import com.tahanebti.ffkmda.base.BaseRepository;

public interface PhoneRepository extends BaseRepository<Phone, Long> {
	
	//@Query(value = "select phones.* FROM PHONES phones, USERS_ROLES ur WHERE roles.id = ur.role_id and ur.user_id = :id", nativeQuery = true)
    //Set<Phone> findPhonesByUserId(@Param("id") Long id);

}

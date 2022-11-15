package com.tahanebti.ffkmda.profile;

import com.tahanebti.ffkmda.base.BaseService;

public interface ProfileService extends BaseService<UserProfile, Long>{
	public UserProfile customProfileCreate(CreateProfileRequest request);
}


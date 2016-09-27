package com.tincore.gsp.server.service;

import com.tincore.gsp.server.domain.UserProfile;

public interface UserProfileService {

	void delete(String username);

	UserProfile create(String username);

	UserProfile getUserProfile(String username);

	UserProfile getOrCreateUserProfile(String string);


}
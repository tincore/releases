package com.tincore.gsp.server.service;

import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.UserProfile;

public interface SessionGlobalRepositoryExtended extends CrudRepositoryExtended<SessionGlobal> {

	void deleteFullByUserProfile(UserProfile userProfile);

	void refresh(SessionGlobal entity);

	void flush();

	SessionGlobal getByUsername(String username);

	SessionGlobal create(UserProfile userProfile);
}

package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.UserProfile;

public interface SessionGlobalRepository extends PagingAndSortingRepository<SessionGlobal, UUID>, SessionGlobalRepositoryExtended {

	Optional<SessionGlobal> findOneByUserProfileUsername(String username);

	void deleteByUserProfile(UserProfile userProfile);

	SessionGlobal findOneByUserProfile(UserProfile userProfile);

	void refresh(SessionGlobal session);

	
}
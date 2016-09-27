package com.tincore.gsp.server.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.UserProfile;

public class SessionGlobalRepositoryImpl extends EntityGlobalRepositoryImpl<SessionGlobal> implements SessionGlobalRepositoryExtended {

	@Autowired
	private TrackPointRepository trackPointRepository;

	@Autowired
	private TrackPeriodRepository trackPeriodRepository;

	@Autowired
	private SessionGlobalRepository sessionGlobalRepository;

	
	@Transactional
	public void deleteFullByUserProfile(UserProfile userProfile) {
		trackPointRepository.deleteByTrackPeriodSessionUserProfile(userProfile);
		trackPeriodRepository.deleteBySessionUserProfile(userProfile);
		sessionGlobalRepository.deleteByUserProfile(userProfile);
	}


	@Override
	public SessionGlobal getByUsername(String username) {
		return sessionGlobalRepository.findOneByUserProfileUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	
	@Override
	public SessionGlobal create(UserProfile userProfile) {
		SessionGlobal sessionGlobal = new SessionGlobal();
		sessionGlobal.setUserProfile(userProfile);
		return sessionGlobalRepository.save(sessionGlobal);
	}
}

package com.tincore.gsp.server.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tincore.gsp.server.domain.AbstractSession;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.TrackPeriodForm;

public interface TrackPeriodRepository extends PagingAndSortingRepository<TrackPeriod, UUID>, TrackPeriodRepositoryExtended {

	void deleteBySession(Session session);

	void deleteBySessionUserProfile(UserProfile userProfile);

	Optional<TrackPeriod> findByIdAndSessionIdAndSessionUserProfileUsername(UUID uuid, UUID sessionUuid, String username);

	Optional<TrackPeriod> findByIdAndSessionUserProfileUsername(UUID uuid, String username);

	Optional<TrackPeriod> findByDateStartAndSessionId(Date dateStart, UUID sessionUuid);

	List<TrackPeriod> findBySession(AbstractSession session);

	Page<TrackPeriod> findBySessionIdAndSessionUserProfileUsername(UUID uuid, String username, Pageable pageable);
}
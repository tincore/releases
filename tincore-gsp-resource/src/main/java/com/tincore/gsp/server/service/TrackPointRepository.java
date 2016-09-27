package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.domain.UserProfile;

public interface TrackPointRepository extends PagingAndSortingRepository<TrackPoint, UUID> {

	Page<TrackPoint> findByTrackPeriodIdAndTrackPeriodSessionUserProfileUsername(UUID uuid, String username, Pageable pageable);

	Optional<TrackPoint> findOneByIdAndTrackPeriodIdAndTrackPeriodSessionUserProfileUsername(UUID uuid, UUID trackPeriodUuid, String username);

	Optional<TrackPoint> findOneByIdAndTrackPeriodSessionUserProfileUsername(UUID uuid, String username);

	@Modifying
	@Query(value="delete from TrackPoint t where t.trackPeriod = :trackPeriod")
	void deleteByTrackPeriod(@Param("trackPeriod") TrackPeriod trackPeriod);

	void deleteByTrackPeriodSessionUserProfile(UserProfile userProfile);

	void deleteByTrackPeriodSession(Session session);

	int countByTrackPeriod(TrackPeriod period);

}
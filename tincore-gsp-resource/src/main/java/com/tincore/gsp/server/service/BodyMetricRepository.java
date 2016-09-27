package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tincore.gsp.server.domain.BodyMetric;
import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.UserProfile;

public interface BodyMetricRepository extends PagingAndSortingRepository<BodyMetric, UUID> {

	Optional<BodyMetric> findOneByIdAndUserProfileUsername(UUID id, String username);

	Page<BodyMetric> findByUserProfileUsername(String username, Pageable pageable);

	void deleteByUserProfile(UserProfile userProfile);

}
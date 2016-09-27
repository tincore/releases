package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tincore.gsp.server.domain.Nutrition;
import com.tincore.gsp.server.domain.UserProfile;

public interface NutritionRepository extends PagingAndSortingRepository<Nutrition, UUID> {

	Optional<Nutrition> findOneByIdAndUserProfileUsername(UUID id, String username);

	Page<Nutrition> findByUserProfileUsername(String username, Pageable pageable);

	void deleteByUserProfile(UserProfile userProfile);

}
package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.UserProfile;

public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, UUID>, EquipmentRepositoryExtended {

	Optional<Equipment> findOneByIdAndUserProfileUsername(UUID id, String username);

	Page<Equipment> findByUserProfileUsername(String username, Pageable pageable);

	void deleteByUserProfile(UserProfile userProfile);

}
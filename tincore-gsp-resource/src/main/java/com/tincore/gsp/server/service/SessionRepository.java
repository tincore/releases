package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.UserProfile;

public interface SessionRepository extends PagingAndSortingRepository<Session, UUID>, SessionRepositoryExtended {

	Optional<Session> findOneByIdAndUserProfileUsername(UUID id, String username);

	Page<Session> findByUserProfileUsername(String username, Pageable pageable);

	void deleteByUserProfile(UserProfile userProfile);

	Iterable<Session> findByUserProfile(UserProfile userProfile);

	@Modifying
//	@Modifying(clearAutomatically = true)
	@Query("update Session s set s.equipment = null where s.equipment = :equipment")
	void updateSetEquipmentNullByEquipment(@Param("equipment") Equipment equipment);

}
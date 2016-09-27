package com.tincore.gsp.server.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.tincore.gsp.server.domain.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, UUID> {

	Optional<UserProfile> findByUsername(String username);
}
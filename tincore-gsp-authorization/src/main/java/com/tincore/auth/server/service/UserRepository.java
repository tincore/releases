package com.tincore.auth.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tincore.auth.server.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	@Override
	List<User> findAll();
	
	@Override
	void delete(User user);
}

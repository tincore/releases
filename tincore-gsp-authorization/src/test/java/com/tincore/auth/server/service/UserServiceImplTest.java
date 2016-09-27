package com.tincore.auth.server.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tincore.auth.server.domain.User;

public class UserServiceImplTest {

	private UserServiceImpl userService = new UserServiceImpl();
	
	@Mock
	private PasswordEncoder mockPasswordEncoder;
	@Mock
	private UserRepository mockUserRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);

		userService.passwordEncoder = mockPasswordEncoder;
		userService.userRepository = mockUserRepository;
	}
	
	@Test
	public void testIsRolePresentWhenGrantedAuthoritiesContainsRoleThenReturnsTrue() {
		assertTrue(userService.isRolePresent(new User("name", "pass", UserService.ROLE_ADMIN), UserService.ROLE_ADMIN));
	}

	@Test
	public void testIsRolePresentWhenGrantedAuthoritiesDoesNotContainRoleThenReturnsFalse() {
		assertFalse(userService.isRolePresent(new User("name", "pass", UserService.ROLE_ADMIN), "otherrole"));
	}

	@Test
	public void testIsRolePresentWhenGrantedAuthoritiesAreEmptyThenReturnsFalse() {
		assertFalse(userService.isRolePresent(new User("name", "pass"), "otherrole"));
	}

	
	
}

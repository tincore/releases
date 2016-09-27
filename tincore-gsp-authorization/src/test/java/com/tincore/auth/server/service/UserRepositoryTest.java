package com.tincore.auth.server.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.auth.server.IntegrationTestFixtureHelper;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.auth.server.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TincoreAuthorizationServerApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private UserRepository userRepository;

	private User user;

	@Before
	public void setup() {
		String username = "user" + System.nanoTime();
		user = testFixtureHelper.createUserIfNotExists(username, "password", "SOME_ROLE");
	}

	@Test
	public void testFindByUsernameThenReturnsUserWithUsername() throws Exception {
		assertTrue(userRepository.findByUsername(user.getUsername()).isPresent());
	}

	@Test
	public void testFindOneByIdAndUserProfileUserUsernameGivenUserWithDifferentEquipmentThenRetunsEmpty() throws Exception {
		assertFalse(userRepository.findByUsername("bad_name").isPresent());
	}

}

package com.tincore.auth.server.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.isPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;

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
import com.tincore.auth.server.form.UserRegisterForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TincoreAuthorizationServerApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class UserServiceTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCreateUserGivenNewUserThenCreatesUserWithUserRoleInDatabase() throws Exception {
//		testFixtureHelper.setAuthenticationUserAdmin();

		String username = "user" + System.nanoTime();

		User user = userService.createUser(new UserRegisterForm(username, "password"));

		assertThat(user.getUsername(), is(equalTo(username)));

		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getAuthorities().stream().map((a) -> {
			return a.getAuthority();
		}).collect(Collectors.toList()), hasItem(UserService.ROLE_USER));
	}

	@Test
	public void testDeleteThenRemovesSuccesfully() throws Exception {
		User user = testFixtureHelper.createUserTest();

		userService.delete(user.getUsername());

		assertThat(userRepository.findOne(user.getId()), is(nullValue()));

	}

}

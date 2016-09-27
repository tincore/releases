package com.tincore.auth.server.web.controller.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.tincore.auth.server.IntegrationTestFixtureHelper;
import com.tincore.auth.server.TestConfiguration;
import com.tincore.auth.server.TincoreAuthorizationServerApplication;
import com.tincore.auth.server.domain.User;
import com.tincore.auth.server.form.UserEditForm;
import com.tincore.auth.server.form.UserRegisterForm;
import com.tincore.auth.server.service.UserRepository;
import com.tincore.auth.server.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TincoreAuthorizationServerApplication.class, TestConfiguration.class})
@ActiveProfiles("test")
public class UsersRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	private User testUser;
	private User testUser2;

	private void assertUserJson(ResultActions resultActions, String parentNodePath, User user) throws Exception {
		resultActions.andExpect(jsonPath(parentNodePath + ".username", is(user.getUsername()))) //
				.andExpect(jsonPath(parentNodePath + ".firstName", is(user.getFirstName()))) //
				.andExpect(jsonPath(parentNodePath + ".lastName", is(user.getLastName())));
	}

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		testUser = testFixtureHelper.createUserTest();
		testUser2 = testFixtureHelper.createUserIfNotExists("user_test2", "somepassword", UserService.ROLE_USER);
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetUserGivenValidUserNameAndAdminUserThenReturnsUser() throws Exception {
		testFixtureHelper.setAuthenticationUserAdmin();

		ResultActions resultActions = mockMvc.perform(get(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + testUser.getUsername()))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson));//
		assertUserJson(resultActions, "$", testUser);
	}

	@Test(expected = NestedServletException.class) // TODO: add rule for expected cause // AccessDeniedException
	public void testGetUserGivenValidUserNameAndDifferentUserThenReturnsForbidden() throws Exception {
		testFixtureHelper.setAuthenticationUserTest();
		mockMvc.perform(get(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + testUser2.getUsername())).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}

	@Test
	public void testGetUserGivenValidUserNameAndSameUserThenReturnsUser() throws Exception {
		testFixtureHelper.setAuthenticationUserTest();

		ResultActions resultActions = mockMvc.perform(get(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + testUser.getUsername()))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson));
		assertUserJson(resultActions, "$", testUser);
	}

	@Test
	public void testGetUsersGivenAuthenticatedAsAdminThenReturnsListOfUsersInDatabase() throws Exception {
		testFixtureHelper.setAuthenticationUserAdmin();

		List<User> users = userRepository.findAll();

		ResultActions resultActions = mockMvc.perform(get(IntegrationTestFixtureHelper.API_PREFIX + "/users"))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$", hasSize(users.size())));

		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			assertUserJson(resultActions, "$[" + i + "]", user);
		}
	}

	@Test
	public void testPostUserGivenAuthenticatedAsAdminThenAddsNewUser() throws Exception {
		testFixtureHelper.setAuthenticationUserAdmin();

		String username = "user" + System.nanoTime();
		String jsonContent = testFixtureHelper.toJson(new UserRegisterForm(username, "somePassword"));
		mockMvc.perform(post(IntegrationTestFixtureHelper.API_PREFIX + "/users")//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated()).andExpect(jsonPath("$").exists()).andExpect(jsonPath("$", is(equalTo(username))));
	}

	@Test(expected = NestedServletException.class)
	public void testPostUserGivenNoAuthenticatedAsAdminThenThrowsAccessDenied() throws Exception {
		testFixtureHelper.setAuthenticationUserTest();

		mockMvc.perform(post(IntegrationTestFixtureHelper.API_PREFIX + "/users").contentType(IntegrationTestFixtureHelper.contentTypeJson)
				.content(testFixtureHelper.toJson(new UserRegisterForm("user" + System.nanoTime(), "somePassword"))));
	}

	@Test
	public void testDeleteUserGivenAuthenticatedAsAdminThenDeletesUser() throws Exception {
		String username = "user" + System.nanoTime();
		testFixtureHelper.createUserIfNotExists(username, "somePassword", "ROLE_DUMMY");

		testFixtureHelper.setAuthenticationUserAdmin();
		mockMvc.perform(delete(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + username)).andExpect(status().isOk());

		Optional<User> user = userRepository.findByUsername(username);
		assertFalse(user.isPresent());
	}

	@Test(expected = NestedServletException.class)
	public void testDeleteUserGivenNoAuthenticatedAsAdminThenThrowsAccessDenied() throws Exception {
		String username = "user" + System.nanoTime();
		testFixtureHelper.createUserIfNotExists(username, "somePassword", "ROLE_DUMMY");

		testFixtureHelper.setAuthenticationUserTest();
		mockMvc.perform(delete(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + username)).andExpect(status().isOk());
	}

	@Test
	public void testDeleteUserGivenAuthenticatedAsAdminAndSameUserThenReturnsError() throws Exception {
		User userAdmin = testFixtureHelper.createUserAdmin();
		testFixtureHelper.setAuthenticationUserAdmin();
		mockMvc.perform(delete(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + userAdmin.getUsername())).andExpect(status().isInternalServerError());
	}

	@Test
	public void testPutUserGivenAuthenticatedAsAdminThenUpdatesUser() throws Exception {
		String username = "user" + System.nanoTime();
		testFixtureHelper.createUserIfNotExists(username, "somePassword", "ROLE_DUMMY");
		UserEditForm userUpdateForm = new UserEditForm();
		userUpdateForm.setFirstName(username + "FN");
		userUpdateForm.setLastName(username + "LN");
		String jsonContent = testFixtureHelper.toJson(userUpdateForm);

		testFixtureHelper.setAuthenticationUserAdmin();
		mockMvc.perform(put(IntegrationTestFixtureHelper.API_PREFIX + "/users/" + username).contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent)).andExpect(status().isOk());

		User user = userRepository.findByUsername(username).get();

		assertThat(user.getFirstName(), is(equalTo(userUpdateForm.getFirstName())));
		assertThat(user.getLastName(), is(equalTo(userUpdateForm.getLastName())));
	}
}

// [
// {
// "id":1,
// "username":"admin",
// "firstName":null,
// "lastName":null,
// "enabled":true,
// "accountLocked":false,
// "accountExpired":false,
// "credentialsExpired":false,
// "userAuthorities":[
// {
// "authority":"ROLE_USER"
// },
// {
// "authority":"ROLE_ADMIN"
// }
// ],
// "authorities":[
// {
// "authority":"ROLE_USER"
// },
// {
// "authority":"ROLE_ADMIN"
// }
// ],
// "accountNonExpired":true,
// "accountNonLocked":true,
// "credentialsNonExpired":true
// },
// {
// "id":2,
// "username":"user",
// "firstName":null,
// "lastName":null,
// "enabled":true,
// "accountLocked":false,
// "accountExpired":false,
// "credentialsExpired":false,
// "userAuthorities":[
// {
// "authority":"ROLE_USER"
// }
// ],
// "authorities":[
// {
// "authority":"ROLE_USER"
// }
// ],
// "accountNonExpired":true,
// "accountNonLocked":true,
// "credentialsNonExpired":true
// },
// {
// "id":3,
// "username":"admin_test",
// "firstName":null,
// "lastName":null,
// "enabled":true,
// "accountLocked":false,
// "accountExpired":false,
// "credentialsExpired":false,
// "userAuthorities":[
// {
// "authority":"ROLE_USER"
// },
// {
// "authority":"ROLE_ADMIN"
// },
// {
// "authority":"ROLE_USER"
// }
// ],
// "authorities":[
// {
// "authority":"ROLE_USER"
// },
// {
// "authority":"ROLE_ADMIN"
// },
// {
// "authority":"ROLE_USER"
// }
// ],
// "accountNonExpired":true,
// "accountNonLocked":true,
// "credentialsNonExpired":true
// }
// ]
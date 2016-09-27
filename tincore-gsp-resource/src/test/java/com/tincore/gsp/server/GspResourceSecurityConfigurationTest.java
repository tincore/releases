package com.tincore.gsp.server;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.web.controller.rest.ProfilesRestController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class GspResourceSecurityConfigurationTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private ProfilesRestController userProfilesRestController;

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testUserProfilesRestControllerDoGetGivenUserAuthenticatedWithAdminRoleThenExecutesSuccesfully() throws Exception {
		UserProfile user = testFixtureHelper.createUserProfile();
		testFixtureHelper.setAuthenticationUserAdmin();
		assertThat(userProfilesRestController.doGet(user.getUsername()), notNullValue());
	}

	@Test
	public void testUserProfilesRestControllerDoGetGivenUserAuthenticatedWithSameUsernameThenExecutesSuccesfully() throws Exception {
		UserProfile user = testFixtureHelper.createUserProfile();
		testFixtureHelper.setAuthentication(user.getUsername(), "password");
		assertThat(userProfilesRestController.doGet(user.getUsername()), notNullValue());
	}
}
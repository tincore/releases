package com.tincore.gsp.server.service;

import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.isPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.UserProfile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class UserProfileServiceTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private SessionGlobalRepository sessionGlobalRepository;

	@Test
	public void testRegisterUserGivenNewUserThenCreatesUserInDatabaseAndUserProfileAndSessionGlobal() throws Exception {
		testFixtureHelper.setAuthenticationUserAdmin();

		String role = "SOME_ROLE";
		String username = "user" + System.nanoTime();

		UserProfile userProfile = userProfileService.create(username);

		assertThat(userProfile.getUsername(), is(equalTo(username)));

		Optional<SessionGlobal> sessionGlobal = sessionGlobalRepository.findOneByUserProfileUsername(username);
		assertTrue(sessionGlobal.isPresent());
		assertThat(sessionGlobal.get().getUserProfile().getId(), is(equalTo(userProfile.getId())));
	}

	@Test
	public void testDeleteGivenUserProfileWithNutritionsAndEquipmentsAndSessionsAndBodyMetricsThenRemovesSuccesfully() throws Exception {
		UserProfile userProfile = testFixtureHelper.createUserProfile();
		testFixtureHelper.createSessions(1, userProfile, null);
		testFixtureHelper.createNutrition(new Date(), userProfile);
		testFixtureHelper.createBodyMetric(new Date(), userProfile, 5);
		testFixtureHelper.createEquipment("equipment", userProfile);

		testFixtureHelper.clearFlushJpa();
		
		userProfileService.delete(userProfile.getUsername());

		assertThat(userProfileRepository.findByUsername(userProfile.getUsername()).isPresent(), is(false));

		
		
	}

}

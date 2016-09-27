package com.tincore.gsp.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.UserProfile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class SessionRepositoryTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private SessionRepository sessionRepository;

	private List<Session> sessions;

	private UserProfile userProfile;

	@Before
	public void setup() {
		userProfile = testFixtureHelper.createUserProfile();
		sessions = testFixtureHelper.createSessions(1, userProfile, null);
	}

	@Test
	public void testFindByUserProfileUsernameThenReturnsSessionsForUser() throws Exception {
		Page<Session> page = sessionRepository.findByUserProfileUsername(userProfile.getUsername(), testFixtureHelper.createPageRequest());
		assertThat(page.getContent(), hasSize(1));
		assertThat(page.iterator().next().getName(), is(equalTo(sessions.get(0).getName())));
	}

	@Test
	public void testFindOneByIdAndUserProfileUsernameGivenUserWithEquipmentThenRetunsSession() throws Exception {
		assertTrue(sessionRepository.findOneByIdAndUserProfileUsername(sessions.get(0).getId(), userProfile.getUsername()).isPresent());
	}

	@Test
	public void testFindOneByIdAndUserProfileUsernameGivenUserWithDifferentEquipmentThenRetunsEmpty() throws Exception {
		assertFalse(sessionRepository.findOneByIdAndUserProfileUsername(sessions.get(0).getId(), "user2").isPresent());
	}

	@Test
	@Transactional
	public void testDeleteByUserProfileThenRemovesRelatedSessions() throws Exception {
		List<Session> currentSessions = sessionRepository.findByUserProfileUsername(userProfile.getUsername(), testFixtureHelper.createPageRequest()).getContent();
		assertThat(currentSessions, hasSize(1));
		assertThat(currentSessions.get(0).getTrackPeriods(), is(not(empty())));
		assertThat(currentSessions.get(0).getTrackPeriods().get(0).getTrackPoints(), is(not(empty())));
		sessionRepository.deleteByUserProfile(userProfile);
		assertThat(sessionRepository.findByUserProfileUsername(userProfile.getUsername(), testFixtureHelper.createPageRequest()).getContent(), hasSize(0));
	}

	@Test
	@Transactional
	public void testDeleteFullThenRemovesSessionWithChildEntitites() throws Exception {
		Session currentSession = sessions.get(0);
		assertThat(currentSession.getTrackPeriods(), is(not(empty())));
		assertThat(currentSession.getTrackPeriods().get(0).getTrackPoints(), is(not(empty())));
		sessionRepository.delete(currentSession);
		assertThat(sessionRepository.findOne(currentSession.getId()), is(nullValue()));
	}
}

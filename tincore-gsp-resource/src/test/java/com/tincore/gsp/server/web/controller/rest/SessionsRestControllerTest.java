package com.tincore.gsp.server.web.controller.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.SessionExtendedForm;
import com.tincore.gsp.server.form.SessionForm;
import com.tincore.gsp.server.form.TrackPeriodExtendedForm;
import com.tincore.gsp.server.form.TrackPointForm;
import com.tincore.gsp.server.service.SessionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class SessionsRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	@Autowired
	private SessionRepository sessionRepository;

	private UserProfile userProfile;

	private List<Session> sessions;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();
		sessions = testFixtureHelper.createSessions(1, userProfile, null);
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetGivenUserThenReturnsSessions() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/sessions/", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(sessions.size())))//
				.andExpect(jsonPath("$.number", is(0)))//
				.andExpect(jsonPath("$.size", is(pageSize))).andExpect(jsonPath("$.totalPages", is(equalTo(1))))//
				.andExpect(jsonPath("$.totalElements", is(equalTo(sessions.size())))).andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(sessions.get(0).getId())))))//
				.andExpect(jsonPath("$.content[0].name", is(equalTo(sessions.get(0).getName()))));
	}

	@Test
	public void testGetGivenSessionIdReturnsSession() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		Session session = sessions.get(0);
		mockMvc.perform(get(String.format("%s/profiles/%s/sessions/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(session.getId())))))//
				.andExpect(jsonPath("$.name", is(equalTo(session.getName()))));
	}

	@Test
	public void testPostThenCreatesSession() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		SessionForm form = new SessionForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(new Date());
		form.setName("test_created_" + System.nanoTime());
		String jsonContent = testFixtureHelper.toJson(form);

		ResultActions resultActions = mockMvc
				.perform(post(String.format("%s/profiles/%s/sessions", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
						.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists())//
		// .andDo(MockMvcResultHandlers.print())//
		;

		String id = testFixtureHelper.fromJson(resultActions, "$.id", String.class);
		Session sessionCreated = sessionRepository.findOne(UUID.fromString(id));
		assertThat(sessionCreated.getName(), is(equalTo(form.getName())));
	}

	@Test
	@Transactional
	public void testPutThenUpdatesSession() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		Session session = testFixtureHelper.createSessions(1, userProfile, null).get(0);

		SessionForm form = new SessionForm();
		form.setId(UUID.randomUUID());
		form.setName("new name");
		form.setDateStart(session.getDateStart());
		String jsonContent = testFixtureHelper.toJson(form);

		mockMvc.perform(put(String.format("%s/profiles/%s/sessions/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(session.getId())))));

		Session sessionUpdated = sessionRepository.findOne(session.getId());
		assertThat(sessionUpdated.getName(), is(equalTo(form.getName())));
	}

	@Test
	public void testDeleteGivenSessionIdThenRemovesSession() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		Session session = testFixtureHelper.createSessions(1, userProfile, null).get(0);
		mockMvc.perform(delete(String.format("%s/profiles/%s/sessions/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId())))//
				.andExpect(status().isOk());
	}
	
	
	@Test
	@Transactional
	public void testPostExtendedThenCreatesSession() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		SessionExtendedForm form = createSessionExtendedForm();
		String jsonContent = testFixtureHelper.toJson(form);
		
		ResultActions resultActions = mockMvc
				.perform(post(String.format("%s/profiles/%s/sessions_extended", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
						.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists())//
		// .andDo(MockMvcResultHandlers.print())//
		;

		String id = testFixtureHelper.fromJson(resultActions, "$.id", String.class);
		Session sessionCreated = sessionRepository.findOne(UUID.fromString(id));
		assertThat(sessionCreated.getName(), is(equalTo(form.getName())));
		assertThat(sessionCreated.getTrackPeriods().get(0).getDateStart().getTime(), is(equalTo(form.getTrackPeriods().get(0).getDateStart().getTime())));
		assertThat(sessionCreated.getTrackPeriods().get(0).getTrackPoints().get(0).getDate().getTime(), 
				is(equalTo(form.getTrackPeriods().get(0).getTrackPoints().get(0).getDate().getTime())));
	}

	@Test
	@Transactional
	public void testPutExtendedThenUpdatesSession() throws Exception {	
		testFixtureHelper.setAuthentication(userProfile);

		Session session = testFixtureHelper.createSessions(1, userProfile, null).get(0);
		SessionExtendedForm form = createSessionExtendedForm();
		String jsonContent = testFixtureHelper.toJson(form);
		
		ResultActions resultActions = mockMvc
				.perform(put(String.format("%s/profiles/%s/sessions_extended/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId()))//
						.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$").exists())//
		// .andDo(MockMvcResultHandlers.print())//
		;

		String id = testFixtureHelper.fromJson(resultActions, "$.id", String.class);
		Session sessionUpdated = sessionRepository.findOne(UUID.fromString(id));
		assertThat(sessionUpdated.getName(), is(equalTo(form.getName())));
	}

	private SessionExtendedForm createSessionExtendedForm() {
		SessionExtendedForm form = new SessionExtendedForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(new Date());
		form.setName("test_created_" + System.nanoTime());

		TrackPeriodExtendedForm trackPeriod = new TrackPeriodExtendedForm();
		trackPeriod.setDateStart(DateUtils.truncate(new Date(1000), Calendar.SECOND));
		trackPeriod.setDateEnd(DateUtils.truncate(new Date(5000), Calendar.SECOND));

		TrackPointForm trackPoint = new TrackPointForm();
		trackPoint.setDate(DateUtils.truncate(new Date(4000), Calendar.SECOND));
		trackPeriod.getTrackPoints().add(trackPoint);

		form.getTrackPeriods().add(trackPeriod);
		return form;
	}
}

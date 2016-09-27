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
import java.util.UUID;

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
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.TrackPeriodForm;
import com.tincore.gsp.server.service.TrackPeriodRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class ProfilesSessionsPeriodsControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	@Autowired
	private TrackPeriodRepository trackPeriodRepository;

	private UserProfile userProfile;

	private Session session;

	private TrackPeriod period;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();
		session = testFixtureHelper.createSession(userProfile);
		period = session.getTrackPeriods().get(0);
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetReturnsSessionTrackPeriods() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/sessions/%s/periods", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(session.getTrackPeriods().size())))//
				.andExpect(jsonPath("$.number", is(0)))//
				.andExpect(jsonPath("$.size", is(pageSize))).andExpect(jsonPath("$.totalPages", is(equalTo(1))))//
				.andExpect(jsonPath("$.totalElements", is(equalTo(session.getTrackPeriods().size()))))//
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(period.getId())))));
	}

	@Test
	public void testGetByIdReturnsTrackPeriod() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/sessions/%s/periods/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId(), period.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))));//
	}

	@Test
	public void testPostThenCreates() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodForm form = new TrackPeriodForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(DateUtils.truncate(new Date(), Calendar.SECOND));
		String jsonContent = testFixtureHelper.toJson(form);

		ResultActions resultActions = mockMvc
				.perform(post(String.format("%s/profiles/%s/sessions/%s/periods", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId()))//
						.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists())//
		// .andDo(MockMvcResultHandlers.print())//
		;

		String id = testFixtureHelper.fromJson(resultActions, "$.id", String.class);
		TrackPeriod entityCreated = trackPeriodRepository.findOne(UUID.fromString(id));
		assertThat(entityCreated.getDateStart().getTime(), is(equalTo(form.getDateStart().getTime())));
		assertThat(entityCreated.getSession().getId(), is(equalTo(session.getId())));
	}

	@Test
	// @Transactional
	public void testPutThenUpdates() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodForm form = new TrackPeriodForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(new Date());
		form.setSleepOverrideStart(new Date());
		String jsonContent = testFixtureHelper.toJson(form);

		mockMvc.perform(put(String.format("%s/profiles/%s/sessions/%s/periods/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId(), period.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))));

		TrackPeriod entityUpdated = trackPeriodRepository.findOne(period.getId());
		assertThat(entityUpdated.getDateStart(), is(equalTo(entityUpdated.getDateStart())));
	}

	@Test
	// @Transactional
	public void testDeleteThenDeletes() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		Session session = testFixtureHelper.createSessions(1, userProfile, null).get(0);
		period = session.getTrackPeriods().get(0);
		mockMvc.perform(delete(String.format("%s/profiles/%s/sessions/%s/periods/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), session.getId(), period.getId())))//
				.andExpect(status().isOk());
	}
}

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
import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.TrackPointForm;
import com.tincore.gsp.server.service.SessionGlobalRepository;
import com.tincore.gsp.server.service.TrackPointRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class PeriodsPointsRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	@Autowired
	private TrackPointRepository trackPointRepository;

	@Autowired
	private SessionGlobalRepository sessionGlobalRepository;

	private UserProfile userProfile;

	private SessionGlobal session;


	private List<TrackPeriod> periods;

	private TrackPeriod period;

	private TrackPoint point;

	private List<TrackPoint> points;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();

		session = sessionGlobalRepository.findOneByUserProfile(userProfile);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		periods = testFixtureHelper.createTrackPeriods(calendar, session, false);
		period = periods.get(0);
		
		
		
		Date dateStart = calendar.getTime();
		calendar.add(Calendar.MINUTE, 1);
		Date dateEnd = calendar.getTime();
		points = testFixtureHelper.createTrackPoints(dateStart, dateEnd,  period, true);
		point = points.get(0);
		
		sessionGlobalRepository.flush();
		sessionGlobalRepository.refresh(session);
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	@Transactional
	public void testGetThenReturnsEntities() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/periods/%s/points", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(points.size())))//
				.andExpect(jsonPath("$.number", is(0)))//
				.andExpect(jsonPath("$.size", is(pageSize))).andExpect(jsonPath("$.totalPages", is(equalTo(1))))//
				.andExpect(jsonPath("$.totalElements", is(equalTo(points.size()))))//
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(point.getId())))));
	}

	@Test
	@Transactional
	public void testGetByIdThenReturnsEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/periods/%s/points/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId(), point.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(point.getId())))));//
	}

	@Test
	@Transactional
	public void testPostThenCreatesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPointForm form = new TrackPointForm();
		form.setId(UUID.randomUUID());
		form.setDate(DateUtils.truncate(new Date(), Calendar.SECOND));
		String jsonContent = testFixtureHelper.toJson(form);

		ResultActions resultActions = mockMvc
				.perform(post(String.format("%s/profiles/%s/periods/%s/points", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId()))//
						.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists())//
		// .andDo(MockMvcResultHandlers.print())//
		;

		String id = testFixtureHelper.fromJson(resultActions, "$.id", String.class);
		TrackPoint entityCreated = trackPointRepository.findOne(UUID.fromString(id));
		assertThat(entityCreated.getDate(), is(equalTo(form.getDate())));
		assertThat(entityCreated.getTrackPeriod().getId(), is(equalTo(period.getId())));
	}

	@Test
	@Transactional
	public void testPutThenUpdatesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPointForm form = new TrackPointForm();
		form.setId(UUID.randomUUID());
		form.setDate(DateUtils.truncate(new Date(), Calendar.SECOND));
		form.setActivityLevel(10);
		String jsonContent = testFixtureHelper.toJson(form);

		mockMvc.perform(put(String.format("%s/profiles/%s/periods/%s/points/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId(), point.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(point.getId())))));

		TrackPoint entityCreated = trackPointRepository.findOne(point.getId());
		assertThat(entityCreated.getDate(), is(equalTo(form.getDate())));
		assertThat(entityCreated.getTrackPeriod().getId(), is(equalTo(point.getTrackPeriod().getId())));
	}

	@Test
	@Transactional
	public void testDeleteThenDeletesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(delete(String.format("%s/profiles/%s/periods/%s/points/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId(), point.getId())))//
				.andExpect(status().isOk());
	}
}

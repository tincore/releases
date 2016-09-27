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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.TrackPeriodExtendedForm;
import com.tincore.gsp.server.form.TrackPeriodForm;
import com.tincore.gsp.server.form.TrackPointForm;
import com.tincore.gsp.server.service.SessionGlobalRepository;
import com.tincore.gsp.server.service.TrackPeriodRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class PeriodsRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	@Autowired
	private SessionGlobalRepository sessionGlobalRepository;

	@Autowired
	private TrackPeriodRepository trackPeriodRepository;

	private UserProfile userProfile;

	private SessionGlobal session;

	private List<TrackPeriod> periods;

	private TrackPeriod period;

	private int periodPointSize;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();
		
		session = sessionGlobalRepository.findOneByUserProfile(userProfile);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.truncate(new Date(), Calendar.SECOND));
		periods = testFixtureHelper.createTrackPeriods(calendar, session, true);
		period=periods.get(0);
		periodPointSize = period.getTrackPoints().size();
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
		SessionGlobal session = sessionGlobalRepository.findOneByUserProfile(userProfile);
		
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/periods", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(session.getTrackPeriods().size())))//
				.andExpect(jsonPath("$.number", is(0)))//
				.andExpect(jsonPath("$.size", is(pageSize))).andExpect(jsonPath("$.totalPages", is(equalTo(1))))//
				.andExpect(jsonPath("$.totalElements", is(equalTo(session.getTrackPeriods().size()))))//
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(session.getTrackPeriods().get(0).getId())))));
	}

	@Test
	@Transactional
	public void testGetByIdThenReturnsEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/periods/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))));//
	}

	@Test
	@Transactional
	public void testPostThenCreatesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodForm form = new TrackPeriodForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(DateUtils.truncate(new Date(), Calendar.SECOND));
		String jsonContent = testFixtureHelper.toJson(form);

		ResultActions resultActions = mockMvc
				.perform(post(String.format("%s/profiles/%s/periods", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
						.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists())//
		// .andDo(MockMvcResultHandlers.print())//
		;

		String id = testFixtureHelper.fromJson(resultActions, "$.id", String.class);
		TrackPeriod entityCreated = trackPeriodRepository.findOne(UUID.fromString(id));
		assertThat(entityCreated.getDateStart(), is(equalTo(form.getDateStart())));
		assertThat(entityCreated.getSession().getId(), is(equalTo(session.getId())));
	}

	@Test
	@Transactional
	public void testPutThenUpdatesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodForm form = new TrackPeriodForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(new Date());
		form.setSleepOverrideStart(new Date());
		String jsonContent = testFixtureHelper.toJson(form);

		mockMvc.perform(put(String.format("%s/profiles/%s/periods/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))));

		TrackPeriod entityUpdated = trackPeriodRepository.findOne(period.getId());
		assertThat(entityUpdated.getDateStart(), is(equalTo(entityUpdated.getDateStart())));
	}

	@Test
	@Transactional
	public void testDeleteThenDeletesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(delete(String.format("%s/profiles/%s/periods/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId())))//
				.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	public void testPutExtendedThenUpdatesEntityAndTrackPoints() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodExtendedForm form = new TrackPeriodExtendedForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(DateUtils.addDays(new Date(), -1)); // Different day
		form.setSleepOverrideStart(new Date());
		TrackPointForm trackPointForm = new TrackPointForm();
		trackPointForm.setDate(period.getTrackPoints().iterator().next().getDate());
		trackPointForm.setActivityLevel(99);
		form.getTrackPoints().add(trackPointForm);
		
		String jsonContent = testFixtureHelper.toJson(form);

		mockMvc.perform(put(String.format("%s/profiles/%s/periods_extended/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))));

		TrackPeriod entityUpdated = trackPeriodRepository.findOne(period.getId());
		assertThat(entityUpdated.getDateStart(), is(equalTo(entityUpdated.getDateStart())));
		assertThat(entityUpdated.getTrackPoints(), hasSize(periodPointSize));
		assertThat(entityUpdated.getTrackPoints().get(0).getActivityLevel(), is(equalTo(trackPointForm.getActivityLevel())));
	}

	@Test
	@Transactional
	public void testGetByIdExtendedThenReturnsEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/periods_extended/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId())))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@Transactional
	public void testPostExtendedThenCreatesEntity() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodExtendedForm form = new TrackPeriodExtendedForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(DateUtils.addDays(new Date(), -1)); // Different day
		form.setSleepOverrideStart(new Date());
		TrackPointForm trackPointForm = new TrackPointForm();
		trackPointForm.setDate(new Date());
		trackPointForm.setActivityLevel(99);
		form.getTrackPoints().add(trackPointForm);
		
		String jsonContent = testFixtureHelper.toJson(form);

		ResultActions resultActions = mockMvc.perform(post(String.format("%s/profiles/%s/periods_extended", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andDo(MockMvcResultHandlers.print())//
				;

		UUID id = UUID.fromString(testFixtureHelper.fromJson(resultActions, "$.id", String.class));

		TrackPeriod entity = trackPeriodRepository.findOne(id);
		assertThat(entity.getDateStart(), is(equalTo(entity.getDateStart())));
		assertThat(entity.getTrackPoints(), hasSize(1));
		assertThat(entity.getTrackPoints().get(0).getActivityLevel(), is(equalTo(trackPointForm.getActivityLevel())));
	}
	
	@Test
	@Transactional
	public void testPostExtendedThenUpdatesExisingEntityWithSameDateStart() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		TrackPeriodExtendedForm form = new TrackPeriodExtendedForm();
		form.setId(UUID.randomUUID());
		form.setDateStart(period.getDateStart());
		// Make sure is last
		form.setSleepOverrideStart(DateUtils.addHours(period.getDateStart(), 1));
		TrackPointForm updateExpectedTrackPointForm = new TrackPointForm();
		updateExpectedTrackPointForm.setDate(period.getDateStart());
		updateExpectedTrackPointForm.setActivityLevel(92);
		form.getTrackPoints().add(updateExpectedTrackPointForm);

		TrackPointForm createExpectedTrackPointForm = new TrackPointForm();
		createExpectedTrackPointForm.setDate(new Date(0));
		createExpectedTrackPointForm.setActivityLevel(91);
		form.getTrackPoints().add(createExpectedTrackPointForm);

		
		String jsonContent = testFixtureHelper.toJson(form);

		mockMvc.perform(post(String.format("%s/profiles/%s/periods_extended", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(period.getId())))))
				.andDo(MockMvcResultHandlers.print())//
				;

		TrackPeriod entity = trackPeriodRepository.findOne(period.getId());
		assertThat(entity.getDateStart(), is(equalTo(entity.getDateStart())));
		assertThat(entity.getTrackPoints(), hasSize(periodPointSize + 1));
		assertThat(entity.getTrackPoints().get(0).getActivityLevel(), is(equalTo(updateExpectedTrackPointForm.getActivityLevel())));
		assertThat(entity.getTrackPoints().get(entity.getTrackPoints().size() -1).getActivityLevel(), is(equalTo(createExpectedTrackPointForm.getActivityLevel())));
	}


}

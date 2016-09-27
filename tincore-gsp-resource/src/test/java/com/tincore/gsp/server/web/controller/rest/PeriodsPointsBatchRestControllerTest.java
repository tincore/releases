package com.tincore.gsp.server.web.controller.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.TrackPointForm;
import com.tincore.gsp.server.service.SessionGlobalRepository;
import com.tincore.gsp.server.service.TrackPeriodRepository;
import com.tincore.gsp.server.service.TrackPointRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class PeriodsPointsBatchRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	@Autowired
	private SessionGlobalRepository sessionGlobalRepository;

	@Autowired
	private TrackPeriodRepository trackPeriodRepository;

	@Autowired
	private TrackPointRepository trackPointRepository;

	private UserProfile userProfile;

	private SessionGlobal session;

	private List<TrackPeriod> periods;

	private TrackPeriod period;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();

		session = sessionGlobalRepository.findOneByUserProfile(userProfile);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		periods = testFixtureHelper.createTrackPeriods(calendar, session, true);
		period = periods.get(0);
		sessionGlobalRepository.flush();
		sessionGlobalRepository.refresh(session);

	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	@Transactional
	public void testPostThenCreates() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		List<TrackPointForm> forms = IntStream.range(0, 5).mapToObj(i -> {
			TrackPointForm form = new TrackPointForm();
			form.setDate(new Date());
			return form;
		}).collect(Collectors.toList());

		String jsonContent = testFixtureHelper.toJson(forms);

		mockMvc.perform(post(String.format("%s/profiles/%s/periods/%s/points_batch", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists())//
				.andDo(MockMvcResultHandlers.print())//
		;
	}

	@Test
	@Transactional
	public void testDeleteThenDeletesAll() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(delete(String.format("%s/profiles/%s/periods/%s/points_batch", GspResourceApplication.API_PREFIX, userProfile.getUsername(), period.getId())))//
				.andExpect(status().isOk());

		trackPeriodRepository.clear();

		TrackPeriod period2 = trackPeriodRepository.findOne(period.getId());
		assertThat(period2.getTrackPoints().size(), is(0));

	}
}

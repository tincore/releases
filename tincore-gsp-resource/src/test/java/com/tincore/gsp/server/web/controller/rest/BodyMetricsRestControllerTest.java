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

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.BodyMetric;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.BodyMetricForm;
import com.tincore.gsp.server.service.BodyMetricRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class BodyMetricsRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	private UserProfile userProfile;

	private List<BodyMetric> bodyMetrics;

	@Autowired
	private BodyMetricRepository bodyMetricRepository;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();
		bodyMetrics = IntStream.range(0, 30).mapToObj(i -> testFixtureHelper.createBodyMetric(new Date(), userProfile, 10)).collect(Collectors.toList());
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetGivenUserThenReturnsBodyMetrics() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/body_metrics", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(pageSize))).andExpect(jsonPath("$.number", is(0))).andExpect(jsonPath("$.size", is(pageSize)))
				.andExpect(jsonPath("$.totalPages", is(equalTo(2)))).andExpect(jsonPath("$.totalElements", is(equalTo(bodyMetrics.size())))) //
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(bodyMetrics.get(0).getId()))))); //
	}

	@Test
	public void testGetGivenPagingUserThenReturnsBodyMetrics() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 5;
		int pageNumber = 1;
		mockMvc.perform(get(String.format("%s/profiles/%s/body_metrics?page=%s&size=%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), pageNumber, pageSize)))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(pageSize))).andExpect(jsonPath("$.number", is(pageNumber))).andExpect(jsonPath("$.size", is(pageSize)))
				.andExpect(jsonPath("$.totalPages", is(equalTo(6)))).andExpect(jsonPath("$.totalElements", is(equalTo(bodyMetrics.size()))))
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(bodyMetrics.get(5).getId())))));//
	}

	@Test
	public void testGetByBodyMetricIdThenReturnsBodyMetric() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/body_metrics/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), bodyMetrics.get(0).getId())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(bodyMetrics.get(0).getId())))));//
	}

	@Test
	public void testPostGivenBodyMetricFormThenBodyMetricIsCreated() throws Exception {
		BodyMetricForm bodyMetricForm = new BodyMetricForm();
		bodyMetricForm.setDate(new Date());
		bodyMetricForm.setWeight(1);
		String jsonContent = testFixtureHelper.toJson(bodyMetricForm);

		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(post(String.format("%s/profiles/%s/body_metrics", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists());
	}

	@Test
	public void testPutGivenBodyMetricFormThenBodyMetricIsUpdatedAndPassedIdIsIgnored() throws Exception {
		BodyMetricForm bodyMetricForm = new BodyMetricForm();
		bodyMetricForm.setId(UUID.randomUUID());
		bodyMetricForm.setDate(new Date());
		bodyMetricForm.setWeight(RandomUtils.nextDouble(0, 100));
		String jsonContent = testFixtureHelper.toJson(bodyMetricForm);

		testFixtureHelper.setAuthentication(userProfile);
		BodyMetric bodyMetricToUpdate = bodyMetrics.get(2);
		mockMvc.perform(put(String.format("%s/profiles/%s/body_metrics/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), bodyMetricToUpdate.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk())//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(String.valueOf(bodyMetricToUpdate.getId()))));

		BodyMetric updateBodyMetric = bodyMetricRepository.findOne(bodyMetricToUpdate.getId());

		assertThat(updateBodyMetric.getWeight(), is(equalTo(bodyMetricForm.getWeight())));

	}

	@Test
	public void testDeleteGivenBodyMetricIdThenRemoves() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(delete(String.format("%s/profiles/%s/body_metrics/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), bodyMetrics.get(0).getId())))//
				.andExpect(status().is(HttpStatus.OK.value()));//
	}

}

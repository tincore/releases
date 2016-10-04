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
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.Nutrition;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.NutritionForm;
import com.tincore.gsp.server.service.NutritionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class NutritionsRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	private List<Nutrition> nutritions;

	@Autowired
	private NutritionRepository nutritionRepository;

	private UserProfile userProfile;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();
		nutritions = IntStream.range(0, 30).mapToObj(i -> testFixtureHelper.createNutrition(new Date(), userProfile)).collect(Collectors.toList());
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetGivenUserThenReturnsNutritions() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/nutritions", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(pageSize))).andExpect(jsonPath("$.number", is(0))).andExpect(jsonPath("$.size", is(pageSize)))
				.andExpect(jsonPath("$.totalPages", is(equalTo(2)))).andExpect(jsonPath("$.totalElements", is(equalTo(nutritions.size()))))
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(nutritions.get(0).getId())))));//
	}

	@Test
	public void testGetGivenPagingUserThenReturnsNutritions() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 5;
		int pageNumber = 1;
		mockMvc.perform(get(String.format("%s/profiles/%s/nutritions?page=%s&size=%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), pageNumber, pageSize)))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(pageSize))).andExpect(jsonPath("$.number", is(pageNumber))).andExpect(jsonPath("$.size", is(pageSize)))
				.andExpect(jsonPath("$.totalPages", is(equalTo(6)))).andExpect(jsonPath("$.totalElements", is(equalTo(nutritions.size()))))
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(nutritions.get(5).getId())))));
	}

	@Test
	public void testGetByNutritionIdThenReturnsNutrition() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/nutritions/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), nutritions.get(0).getId())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(nutritions.get(0).getId())))));
	}

	@Test
	public void testPostGivenNutritionFormThenNutritionIsCreated() throws Exception {
		NutritionForm nutritionForm = new NutritionForm();
		nutritionForm.setDate(new Date());
		nutritionForm.setWater(RandomUtils.nextDouble(0, 100));
		String jsonContent = testFixtureHelper.toJson(nutritionForm);

		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(post(String.format("%s/profiles/%s/nutritions", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists());
	}

	@Test
	public void testPutGivenNutritionFormThenNutritionIsUpdatedAndPassedIdIsIgnored() throws Exception {
		NutritionForm nutritionForm = new NutritionForm();
		nutritionForm.setId(UUID.randomUUID());
		nutritionForm.setDate(new Date());
		nutritionForm.setWater(RandomUtils.nextDouble(0, 100));
		String jsonContent = testFixtureHelper.toJson(nutritionForm);

		testFixtureHelper.setAuthentication(userProfile);
		Nutrition nutritionToUpdate = nutritions.get(2);
		mockMvc.perform(put(String.format("%s/profiles/%s/nutritions/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), nutritionToUpdate.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(String.valueOf(nutritionToUpdate.getId()))));

		Nutrition updateNutrition = nutritionRepository.findOne(nutritionToUpdate.getId());

		assertThat(updateNutrition.getWater(), is(equalTo(nutritionForm.getWater())));

	}

	@Test
	public void testDeleteGivenEquipmentIdThenRemovesEquipment() throws Exception {
		Nutrition nutrition = testFixtureHelper.createNutrition(new Date(), userProfile);
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(delete(String.format("%s/profiles/%s/nutritions/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), nutrition.getId())))//
				.andExpect(status().is(HttpStatus.OK.value()));//
	}

}

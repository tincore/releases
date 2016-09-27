package com.tincore.gsp.server.web.controller.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
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
import org.springframework.web.util.NestedServletException;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.Gender;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.UserProfileForm;
import com.tincore.gsp.server.service.UserProfileRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class ProfileRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private UserProfileRepository userProfileRepository;

	private MockMvc mockMvc;

	private UserProfile userProfile;

	private String userPassword = "pass";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		userProfile = testFixtureHelper.createUserProfile();
		userProfile.setBirthDate(new Date());
		userProfile.setGender(Gender.male);
		userProfile.setHeight(1.83);
		userProfile = userProfileRepository.save(userProfile);
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetGivenValidUserNameAndAdminUserThenReturnsProfile() throws Exception {
		testFixtureHelper.setAuthenticationUserAdmin();

		mockMvc.perform(get(String.format("%s/profiles/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.birthDate", is(testFixtureHelper.toJsonValueDate(userProfile.getBirthDate())))) //
				.andExpect(jsonPath("$.gender", is(String.valueOf(userProfile.getGender())))) //
				.andExpect(jsonPath("$.height", is(userProfile.getHeight()))) //
		;
	}

	@Test
	public void testGetGivenValidUserNameAndSameUserThenReturnsProfile() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);

		mockMvc.perform(get(String.format("%s/profiles/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test(expected = NestedServletException.class) // AccessDeniedException TODO: Add matcher for wrapped
	public void testGetGivenValidUserNameAndDifferentUserThenThrowsException() throws Exception {
		testFixtureHelper.setAuthenticationUserTest();

		mockMvc.perform(get(String.format("%s/profiles/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}

	@Test
	public void testPutProfileGivenAuthenticatedAsSameUserThenUpdatesProfile() throws Exception {
		testFixtureHelper.setAuthentication(userPassword);

		UserProfileForm userProfileForm = new UserProfileForm();
		userProfileForm.setBirthDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		userProfileForm.setGender(Gender.female);
		userProfileForm.setHeight(1.1);
		String jsonContent = testFixtureHelper.toJson(userProfileForm);

		testFixtureHelper.setAuthenticationUserAdmin();
		mockMvc.perform(put(String.format("%s/profiles/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername())) //
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent)).andExpect(status().isOk());

		UserProfile userProfileUpdated = userProfileRepository.findByUsername(userProfile.getUsername()).get();

		assertThat(userProfileUpdated.getBirthDate(), is(equalTo(userProfileForm.getBirthDate())));
		assertThat(userProfileUpdated.getGender(), is(equalTo(userProfileForm.getGender())));
		assertThat(userProfileUpdated.getHeight(), is(equalTo(userProfileForm.getHeight())));

	}
}

// {
// "birthDate":null,
// "gender":male,
// "height":0.0,
// "strideLength":0.0,
// "strideLengthRun":0.0,
// "pedometerDayDistanceTarget":0.0,
// "pedometerDayStepTarget":0,
// "calorieDayTarget":0.0,
// "hydrationDayTarget":0.0,
// "calorieInDayTarget":0.0,
// "sleepDayTarget":0,
// "heartRateMax":0,
// "heartRateRest":0,
// "volumeOxygenMax":0.0,
// "male":true
// }
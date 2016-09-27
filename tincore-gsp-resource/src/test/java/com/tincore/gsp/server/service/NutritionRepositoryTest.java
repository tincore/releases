package com.tincore.gsp.server.service;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.Nutrition;
import com.tincore.gsp.server.domain.UserProfile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class NutritionRepositoryTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private NutritionRepository nutritionRepository;

	private UserProfile userProfile;
	private Nutrition nutrition;

	@Before
	public void setup() {
		userProfile = testFixtureHelper.createUserProfile();
		nutrition = testFixtureHelper.createNutrition(new Date(), userProfile);
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testFindOneByIdAndUserProfileUsernameGivenUserWithEquipmentThenRetunsNutrition() throws Exception {
		assertTrue(nutritionRepository.findOneByIdAndUserProfileUsername(nutrition.getId(), userProfile.getUsername()).isPresent());
	}

	@Test
	public void testFindOneByIdAndUserProfileUsernameGivenUserWithDifferentEquipmentThenRetunsEmpty() throws Exception {
		assertFalse(nutritionRepository.findOneByIdAndUserProfileUsername(nutrition.getId(), "user2").isPresent());
	}

	@Test
	@Transactional
	public void testDeleteByUserProfileThenRemovesRelatedNutritions() throws Exception {
		UserProfile userProfile = testFixtureHelper.createUserProfile();
		testFixtureHelper.createNutrition(new Date(), userProfile);

		assertThat(nutritionRepository.findByUserProfileUsername(userProfile.getUsername(), new PageRequest(0, 20)).getContent(), hasSize(1));

		nutritionRepository.deleteByUserProfile(userProfile);

		assertThat(nutritionRepository.findByUserProfileUsername(userProfile.getUsername(), new PageRequest(0, 20)).getContent(), hasSize(0));
	}

}

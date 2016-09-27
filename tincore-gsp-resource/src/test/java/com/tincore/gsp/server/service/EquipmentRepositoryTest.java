package com.tincore.gsp.server.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.isPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.IntegrationTestFixtureHelper;
import com.tincore.gsp.server.TestConfiguration;
import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.UserProfile;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class EquipmentRepositoryTest {

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Autowired
	private SessionRepository sessionRepository;

	private UserProfile userProfile;

	private Equipment equipment;

	@Before
	public void setup() {
		userProfile = testFixtureHelper.createUserProfile();
		equipment = testFixtureHelper.createEquipment("someName", userProfile);
	}

	@Test
	public void testFindByPersonalProfileUsernameThenReturnsEquipmentsForUser() throws Exception {
		Page<Equipment> equipments = equipmentRepository.findByUserProfileUsername(userProfile.getUsername(), new PageRequest(0, 10));
		assertThat(equipments.getContent(), hasSize(1));
		assertThat(equipments.iterator().next().getName(), is(equalTo(equipment.getName())));
	}

	@Test
	public void testFindOneByIdAndUserProfileUsernameGivenUserWithEquipmentThenRetunsEquipment() throws Exception {
		assertTrue(equipmentRepository.findOneByIdAndUserProfileUsername(equipment.getId(), userProfile.getUsername()).isPresent());
	}

	@Test
	public void testFindOneByIdAndUserProfileUsernameGivenUserWithDifferentEquipmentThenRetunsEmpty() throws Exception {
		assertFalse(equipmentRepository.findOneByIdAndUserProfileUsername(equipment.getId(), "user2").isPresent());
	}

	@Test
	@Transactional
	public void testDeleteByUserProfileThenRemovesRelatedEquipments() throws Exception {
		UserProfile userProfile = testFixtureHelper.createUserProfile();
		testFixtureHelper.createEquipment("other", userProfile);

		assertThat(equipmentRepository.findByUserProfileUsername(userProfile.getUsername(), new PageRequest(0, 20)).getContent(), hasSize(1));

		equipmentRepository.deleteByUserProfile(userProfile);

		assertThat(equipmentRepository.findByUserProfileUsername(userProfile.getUsername(), new PageRequest(0, 20)).getContent(), hasSize(0));
	}

	@Test
	@Transactional
	public void testDeleteFullThenRemovesRelatedSessions() throws Exception {
		Equipment equipment = testFixtureHelper.createEquipment("other", userProfile);
		Session session = testFixtureHelper.createSession(userProfile, equipment);

		assertThat(session.getEquipment(), is(notNullValue()));

		equipmentRepository.deleteFull(equipment);

		sessionRepository.refresh(session);
		assertThat(session.getEquipment(), is(nullValue()));

	}

}

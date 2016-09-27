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
import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.EquipmentForm;
import com.tincore.gsp.server.service.EquipmentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class EquipmentsRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private IntegrationTestFixtureHelper testFixtureHelper;

	private MockMvc mockMvc;

	private UserProfile userProfile;

	private List<Equipment> equipments;

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		userProfile = testFixtureHelper.createUserProfile();
		equipments = IntStream.range(0, 30).mapToObj(i -> testFixtureHelper.createEquipment("equipment_name_" + i, userProfile)).collect(Collectors.toList());
	}

	@After
	public void tearDown() {
		testFixtureHelper.clearAuthentication();
	}

	@Test
	public void testGetGivenUserThenReturnsEquipments() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 20;
		mockMvc.perform(get(String.format("%s/profiles/%s/equipments", GspResourceApplication.API_PREFIX, userProfile.getUsername())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(pageSize))).andExpect(jsonPath("$.number", is(0))).andExpect(jsonPath("$.size", is(pageSize)))
				.andExpect(jsonPath("$.totalPages", is(equalTo(2)))).andExpect(jsonPath("$.totalElements", is(equalTo(equipments.size()))))
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(equipments.get(0).getId()))))).andExpect(jsonPath("$.content[0].name", is(equalTo(equipments.get(0).getName()))));
	}

	@Test
	public void testGetGivenPagingUserThenReturnsEquipments() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		int pageSize = 5;
		int pageNumber = 1;
		mockMvc.perform(get(String.format("%s/profiles/%s/equipments?page=%s&size=%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), pageNumber, pageSize)))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.content", hasSize(pageSize))).andExpect(jsonPath("$.number", is(pageNumber))).andExpect(jsonPath("$.size", is(pageSize)))
				.andExpect(jsonPath("$.totalPages", is(equalTo(6)))).andExpect(jsonPath("$.totalElements", is(equalTo(equipments.size()))))
				.andExpect(jsonPath("$.content[0].id", is(equalTo(String.valueOf(equipments.get(5).getId()))))).andExpect(jsonPath("$.content[0].name", is(equalTo(equipments.get(5).getName()))));
	}

	@Test
	public void testGetByEquipmentIdThenReturnsEquipment() throws Exception {
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(get(String.format("%s/profiles/%s/equipments/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), equipments.get(0).getId())))//
				.andExpect(status().is(HttpStatus.OK.value()))//
				.andExpect(content().contentType(IntegrationTestFixtureHelper.contentTypeJson))//
				.andExpect(jsonPath("$.id", is(equalTo(String.valueOf(equipments.get(0).getId()))))).andExpect(jsonPath("$.name", is(equalTo(equipments.get(0).getName()))));
	}

	@Test
	public void testPostGivenEquipmentFormThenEquipmentIsCreated() throws Exception {
		EquipmentForm equipmentForm = new EquipmentForm();
		equipmentForm.setName("eq_" + System.nanoTime());
		equipmentForm.setWeight(1);
		equipmentForm.setWheelPerimeter(0.3);
		String jsonContent = testFixtureHelper.toJson(equipmentForm);

		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(post(String.format("%s/profiles/%s/equipments", GspResourceApplication.API_PREFIX, userProfile.getUsername()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isCreated())//
				.andExpect(jsonPath("$").exists());
	}

	@Test
	public void testPutGivenEquipmentFormThenEquipmentIsUpdatedAndPassedIdIsIgnored() throws Exception {
		EquipmentForm equipmentForm = new EquipmentForm();
		equipmentForm.setId(UUID.randomUUID());
		equipmentForm.setWeight(RandomUtils.nextDouble(0, 100));
		equipmentForm.setWheelPerimeter(0.4);
		String jsonContent = testFixtureHelper.toJson(equipmentForm);

		testFixtureHelper.setAuthentication(userProfile);
		Equipment equipmentToUpdate = equipments.get(2);
		mockMvc.perform(put(String.format("%s/profiles/%s/equipments/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), equipmentToUpdate.getId()))//
				.contentType(IntegrationTestFixtureHelper.contentTypeJson).content(jsonContent))//
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(String.valueOf(equipmentToUpdate.getId()))));

		Equipment updateEquipment = equipmentRepository.findOne(equipmentToUpdate.getId());

		assertThat(updateEquipment.getWeight(), is(equalTo(equipmentForm.getWeight())));

	}
	
	@Test
	public void testDeleteGivenEquipmentIdThenRemovesEquipment() throws Exception {
		Equipment equipment2 = testFixtureHelper.createEquipment("equipment_name", userProfile);
		testFixtureHelper.setAuthentication(userProfile);
		mockMvc.perform(delete(String.format("%s/profiles/%s/equipments/%s", GspResourceApplication.API_PREFIX, userProfile.getUsername(), equipment2.getId())))//
				.andExpect(status().is(HttpStatus.OK.value()));//
	}

	
}
package com.tincore.gsp.server.web.controller.rest;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.Nutrition;
import com.tincore.gsp.server.form.EquipmentForm;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.NutritionForm;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.SummaryForm;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.NutritionRepository;
import com.tincore.gsp.server.service.UserProfileRepository;

@RestController
public class NutritionsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public UserProfileRepository userProfileRepository;

	@Autowired
	public NutritionRepository nutritionRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/nutritions/{uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doDelete(principal.getName(), uuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/nutritions/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		Nutrition nutrition = nutritionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		nutritionRepository.delete(nutrition);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/nutritions")
	public PageForm<NutritionForm> doGet(Principal principal, Pageable pageable) {
		return doGet(principal.getName(), pageable);
	}

	
	@GetMapping(GspResourceApplication.API_PREFIX + "/nutritions_summary")
	public PageForm<SummaryForm> doGetSummary(Principal principal, Pageable pageable) {
		return doGetSummary(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/nutritions_summary")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<SummaryForm> doGetSummary(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toNutritionSummaryForms(nutritionRepository.findByUserProfileUsername(username, pageable));
	}
	
	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/nutritions")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<NutritionForm> doGet(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toNutritionForms(nutritionRepository.findByUserProfileUsername(username, pageable));
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/nutritions/{uuid}")
	public NutritionForm doGetById(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetById(principal.getName(), uuid);
	}
	
	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/nutritions/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public NutritionForm doGetById(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		return formMapper.toNutritionForm(nutritionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid)));
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/nutritions")
	public ResponseEntity<?> doPost(Principal principal, @RequestBody NutritionForm nutritionForm) {
		return doPost(principal.getName(), nutritionForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/nutritions")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @RequestBody NutritionForm nutritionForm) {
		Nutrition nutrition = formMapper.toNutrition(nutritionForm, userProfileRepository.findByUsername(username).get());
		return new ResponseEntity<>(new OperationResponseForm(nutritionRepository.save(nutrition).getId()), HttpStatus.CREATED);
	}


	@PutMapping(GspResourceApplication.API_PREFIX + "/nutritions/{uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody NutritionForm nutritionForm) {
		return doPut(principal.getName(), uuid, nutritionForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/nutritions/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid, @RequestBody NutritionForm nutritionForm) {
		Nutrition nutrition = nutritionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		formMapper.update(nutritionForm, nutrition);
		return new ResponseEntity<>(new OperationResponseForm(nutritionRepository.save(nutrition).getId()), HttpStatus.OK);
	}


	
}
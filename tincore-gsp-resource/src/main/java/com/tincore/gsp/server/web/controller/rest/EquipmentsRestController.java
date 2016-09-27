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
import com.tincore.gsp.server.form.EquipmentForm;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.SummaryForm;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.EquipmentRepository;
import com.tincore.gsp.server.service.UserProfileRepository;

@RestController
public class EquipmentsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public UserProfileRepository userProfileRepository;

	@Autowired
	public EquipmentRepository equipmentRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/equipments/{uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doDelete(principal.getName(), uuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/equipments/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		Equipment equipment = equipmentRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
//		equipmentRepository.delete(equipment);
		equipmentRepository.deleteFull(equipment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/equipments")
	public PageForm<EquipmentForm> doGet(Principal principal, Pageable pageable) {
		return doGet(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/equipments")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<EquipmentForm> doGet(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toEquipmentForms(equipmentRepository.findByUserProfileUsername(username, pageable));
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/equipments/{uuid}")
	public EquipmentForm doGetById(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetById(principal.getName(), uuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/equipments/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public EquipmentForm doGetById(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		Equipment equipment = equipmentRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		return formMapper.toEquipmentForm(equipment);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/equipments")
	public ResponseEntity<?> doPost(Principal principal, @RequestBody EquipmentForm equipmentForm) {
		return doPost(principal.getName(), equipmentForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/equipments")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @RequestBody EquipmentForm equipmentForm) {
		Equipment equipment = formMapper.toEquipment(equipmentForm, userProfileRepository.findByUsername(username).get());
		return new ResponseEntity<>(new OperationResponseForm(equipmentRepository.save(equipment).getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/equipments/{uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody EquipmentForm equipmentForm) {
		return doPut(principal.getName(), uuid, equipmentForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/equipments/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid, @RequestBody EquipmentForm equipmentForm) {
		Equipment equipment = equipmentRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		formMapper.update(equipmentForm, equipment);
		equipmentRepository.save(equipment);
		return new ResponseEntity<>(new OperationResponseForm(equipment.getId()), HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/equipments_summary")
	public PageForm<SummaryForm> doGetSummary(Principal principal, Pageable pageable) {
		return doGetSummary(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/equipments_summary")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<SummaryForm> doGetSummary(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toEquipmentSummaryForms(equipmentRepository.findByUserProfileUsername(username, pageable));
	}

}
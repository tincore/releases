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
import com.tincore.gsp.server.domain.BodyMetric;
import com.tincore.gsp.server.form.BodyMetricForm;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.SummaryForm;
import com.tincore.gsp.server.service.BodyMetricRepository;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.UserProfileRepository;

@RestController
public class BodyMetricsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public UserProfileRepository userProfileRepository;

	@Autowired
	public BodyMetricRepository bodyMetricRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/body_metrics/{uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doDelete(principal.getName(), uuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/body_metrics/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		bodyMetricRepository.delete(bodyMetricRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid)));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/body_metrics")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<BodyMetricForm> doGet(Principal principal, Pageable pageable) {
		return doGet(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/body_metrics")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<BodyMetricForm> doGet(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toBodyMetricForms(bodyMetricRepository.findByUserProfileUsername(username, pageable));
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/body_metrics/{uuid}")
	public BodyMetricForm doGetById(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetById(principal.getName(), uuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/body_metrics/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public BodyMetricForm doGetById(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		return formMapper.toBodyMetricForm(bodyMetricRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid)));
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/body_metrics")
	public ResponseEntity<?> doPost(@RequestBody BodyMetricForm bodyMetricForm, Principal principal) {
		return doPost(bodyMetricForm, principal.getName());
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/body_metrics")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@RequestBody BodyMetricForm bodyMetricForm, @PathVariable("user_name") String username) {
		BodyMetric bodyMetric = formMapper.toBodyMetric(bodyMetricForm, userProfileRepository.findByUsername(username).get());
		return new ResponseEntity<>(new OperationResponseForm(bodyMetricRepository.save(bodyMetric).getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/body_metrics/{uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody BodyMetricForm bodyMetricForm) {
		return doPut(principal.getName(), uuid, bodyMetricForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/body_metrics/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid, @RequestBody BodyMetricForm bodyMetricForm) {
		BodyMetric bodyMetric = bodyMetricRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		formMapper.update(bodyMetricForm, bodyMetric);
		bodyMetricRepository.save(bodyMetric);
		return new ResponseEntity<>(new OperationResponseForm(bodyMetric.getId()), HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/body_metrics_summary")
	public PageForm<SummaryForm> doGetSummary(Principal principal, Pageable pageable) {
		return doGetSummary(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/body_metrics_summary")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<SummaryForm> doGetSummary(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toBodyMetricSummaryForms(bodyMetricRepository.findByUserProfileUsername(username, pageable));
	}

}
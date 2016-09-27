//

package com.tincore.gsp.server.web.controller.rest;

import java.security.Principal;
import java.util.UUID;

import javax.transaction.Transactional;

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
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.SummaryForm;
import com.tincore.gsp.server.form.TrackPeriodExtendedForm;
import com.tincore.gsp.server.form.TrackPeriodForm;
import com.tincore.gsp.server.service.SessionGlobalRepository;
import com.tincore.gsp.server.service.TrackPeriodRepository;
import com.tincore.gsp.server.service.UserProfileService;

@RestController
public class PeriodsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public UserProfileService userService;

	@Autowired
	public SessionGlobalRepository sessionGlobalRepository;

	@Autowired
	public TrackPeriodRepository trackPeriodRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/periods/{uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doDelete(principal.getName(), uuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		trackPeriodRepository.delete(trackPeriodRepository.getByIdAndSessionUserProfileUsername(username, uuid));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/periods")
	public PageForm<TrackPeriodForm> doGet(Principal principal, Pageable pageable) {
		return doGet(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<TrackPeriodForm> doGet(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toTrackPeriodForms(trackPeriodRepository.findBySessionIdAndSessionUserProfileUsername(sessionGlobalRepository.getByUsername(username).getId(), username, pageable));
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/periods/{uuid}")
	public TrackPeriodForm doGetById(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetById(principal.getName(), uuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public TrackPeriodForm doGetById(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		return formMapper.toTrackPeriodForm(trackPeriodRepository.getByIdAndSessionUserProfileUsername(username, uuid));
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/periods")
	public ResponseEntity<?> doPost(Principal principal, @RequestBody TrackPeriodForm trackPeriodForm) {
		return doPost(principal.getName(), trackPeriodForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @RequestBody TrackPeriodForm trackPeriodForm) {
		return new ResponseEntity<>(new OperationResponseForm(trackPeriodRepository.save(trackPeriodForm, username).getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/periods/{uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody TrackPeriodForm trackPeriodForm) {
		return doPut(principal.getName(), uuid, trackPeriodForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("uuid") UUID periodUuid, @RequestBody TrackPeriodForm trackPeriodForm) {
		TrackPeriod trackPeriod = trackPeriodRepository.getByIdAndSessionUserProfileUsername(username, periodUuid);
		formMapper.update(trackPeriodForm, trackPeriod);
		trackPeriodRepository.save(trackPeriod);
		return new ResponseEntity<>(new OperationResponseForm(trackPeriod.getId()), HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/periods_summary")
	public PageForm<SummaryForm> doGetSummary(Principal principal, Pageable pageable) {
		return doGetSummary(principal.getName(), pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods_summary")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<SummaryForm> doGetSummary(@PathVariable("user_name") String username, Pageable pageable) {
		return formMapper.toTrackPeriodSummaryForms(trackPeriodRepository.findBySessionIdAndSessionUserProfileUsername(sessionGlobalRepository.getByUsername(username).getId(), username, pageable));
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/periods_extended")
	public ResponseEntity<?> doPostExtended(Principal principal, @RequestBody TrackPeriodExtendedForm periodForm) {
		return doPostExtended(principal.getName(), periodForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods_extended")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPostExtended(@PathVariable("user_name") String username, @RequestBody TrackPeriodExtendedForm periodForm) {
		return new ResponseEntity<>(new OperationResponseForm(trackPeriodRepository.saveExtended(periodForm, username).getId()), HttpStatus.CREATED);

	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/periods_extended/{uuid}")
	public ResponseEntity<?> doPutExtended(Principal principal, @PathVariable("uuid") UUID uuid, @RequestBody TrackPeriodExtendedForm periodForm) {
		return doPutExtended(principal.getName(), uuid, periodForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods_extended/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	@Transactional
	public ResponseEntity<?> doPutExtended(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid, @RequestBody TrackPeriodExtendedForm periodForm) {
		return new ResponseEntity<>(new OperationResponseForm(trackPeriodRepository.saveExtended(uuid, periodForm, username).getId()), HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/periods_extended/{uuid}")
	public TrackPeriodExtendedForm doGetByIdExtended(Principal principal, @PathVariable("uuid") UUID uuid) {
		return doGetByIdExtended(principal.getName(), uuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods_extended/{uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public TrackPeriodExtendedForm doGetByIdExtended(@PathVariable("user_name") String username, @PathVariable("uuid") UUID uuid) {
		return formMapper.toTrackPeriodExtendedForm(trackPeriodRepository.getByIdAndSessionUserProfileUsername(username, uuid));
	}

}
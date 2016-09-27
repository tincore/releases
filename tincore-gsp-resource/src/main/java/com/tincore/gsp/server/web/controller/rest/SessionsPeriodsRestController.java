//

package com.tincore.gsp.server.web.controller.rest;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.TrackPeriodForm;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.SessionRepository;
import com.tincore.gsp.server.service.TrackPeriodRepository;

@RestController
public class SessionsPeriodsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public SessionRepository sessionRepository;

	@Autowired
	public TrackPeriodRepository trackPeriodRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid) {
		return doDelete(principal.getName(), sessionUuid, periodUuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid) {
		TrackPeriod trackPeriod = getTrackPeriod(periodUuid, sessionUuid, username);
		trackPeriodRepository.delete(trackPeriod);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods")
	public PageForm<TrackPeriodForm> doGet(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, Pageable pageable) {
		return doGet(principal.getName(), sessionUuid, pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<TrackPeriodForm> doGet(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, Pageable pageable) {
		Page<TrackPeriod> trackPeriods = trackPeriodRepository.findBySessionIdAndSessionUserProfileUsername(sessionUuid, username, pageable);
		return formMapper.toTrackPeriodForms(trackPeriods);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}")
	public TrackPeriodForm doGetById(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid) {
		return doGetById(principal.getName(), sessionUuid, periodUuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public TrackPeriodForm doGetById(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid) {
		TrackPeriod trackPeriod = getTrackPeriod(periodUuid, sessionUuid, username);
		TrackPeriodForm trackPeriodForm = formMapper.toTrackPeriodForm(trackPeriod);
		return trackPeriodForm;
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods")
	public ResponseEntity<?> doPost(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @RequestBody TrackPeriodForm trackPeriodForm) {
		return doPost(principal.getName(), sessionUuid, trackPeriodForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @RequestBody TrackPeriodForm trackPeriodForm) {
		Session session = sessionRepository.findOneByIdAndUserProfileUsername(sessionUuid, username).orElseThrow(() -> new EntityNotFoundException(sessionUuid));
		TrackPeriod trackPeriod = formMapper.toTrackPeriod(trackPeriodForm);
		trackPeriod.setSession(session);
		TrackPeriod savedTrackPeriod = trackPeriodRepository.save(trackPeriod);
		return new ResponseEntity<>(new OperationResponseForm(savedTrackPeriod.getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, @RequestBody TrackPeriodForm trackPeriodForm) {
		return doPut(principal.getName(), sessionUuid, periodUuid, trackPeriodForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@RequestBody TrackPeriodForm trackPeriodForm) {
		TrackPeriod trackPeriod = getTrackPeriod(periodUuid, sessionUuid, username);
		formMapper.update(trackPeriodForm, trackPeriod);
		trackPeriodRepository.save(trackPeriod);
		return new ResponseEntity<>(new OperationResponseForm(trackPeriod.getId()), HttpStatus.OK);
	}

	private TrackPeriod getTrackPeriod(UUID periodUuid, UUID sessionUuid, String username) {
		return trackPeriodRepository.findByIdAndSessionIdAndSessionUserProfileUsername(periodUuid, sessionUuid, username).orElseThrow(() -> new EntityNotFoundException(periodUuid));
	}
}
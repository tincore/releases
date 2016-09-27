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
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.PageForm;
import com.tincore.gsp.server.form.TrackPointForm;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.TrackPeriodRepository;
import com.tincore.gsp.server.service.TrackPointRepository;

@RestController
public class SessionsPeriodsPointsRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public TrackPeriodRepository trackPeriodRepository;

	@Autowired
	public TrackPointRepository trackPointRepository;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}/points/{point_uuid}")
	public ResponseEntity<?> doDelete(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, @PathVariable("point_uuid") UUID pointUuid) {
		return doDelete(principal.getName(), sessionUuid, periodUuid, pointUuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}/points/{point_uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDelete(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@PathVariable("point_uuid") UUID pointUuid) {
		TrackPoint trackPoint = getTrackPoint(pointUuid, periodUuid, sessionUuid, username);
		trackPointRepository.delete(trackPoint);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}/points")
	public PageForm<TrackPointForm> doGet(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, Pageable pageable) {
		return doGet(principal.getName(), sessionUuid, periodUuid, pageable);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}/points")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public PageForm<TrackPointForm> doGet(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, Pageable pageable) {
		Page<TrackPoint> trackPoints = trackPointRepository.findByTrackPeriodIdAndTrackPeriodSessionUserProfileUsername(periodUuid, username, pageable);
		return formMapper.toTrackPointForms(trackPoints);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}/points/{point_uuid}")
	public TrackPointForm doGetById(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, @PathVariable("point_uuid") UUID pointUuid) {
		return doGetById(principal.getName(), sessionUuid, periodUuid, pointUuid);
	}

	@GetMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}/points/{point_uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public TrackPointForm doGetById(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@PathVariable("point_uuid") UUID pointUuid) {
		TrackPoint trackPoint = getTrackPoint(pointUuid, periodUuid, sessionUuid, username);
		return formMapper.toTrackPointForm(trackPoint);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}/points")
	public ResponseEntity<?> doPost(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, @RequestBody TrackPointForm trackPointForm) {
		return doPost(principal.getName(), sessionUuid, periodUuid, trackPointForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}/points")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@RequestBody TrackPointForm trackPointForm) {
		TrackPeriod trackPeriod = trackPeriodRepository.findByIdAndSessionIdAndSessionUserProfileUsername(periodUuid, sessionUuid, username).orElseThrow(() -> new EntityNotFoundException(periodUuid));

		TrackPoint trackPoint = formMapper.toTrackPoint(trackPointForm);
		trackPoint.setTrackPeriod(trackPeriod);
		TrackPoint savedTrackPoint = trackPointRepository.save(trackPoint);
		return new ResponseEntity<>(new OperationResponseForm(savedTrackPoint.getId()), HttpStatus.CREATED);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}/points/{point_uuid}")
	public ResponseEntity<?> doPut(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid, @PathVariable("point_uuid") UUID pointUuid,
			@RequestBody TrackPointForm trackPointForm) {
		return doPut(principal.getName(), sessionUuid, periodUuid, pointUuid, trackPointForm);
	}

	@PutMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}/points/{point_uuid}")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPut(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@PathVariable("point_uuid") UUID pointUuid, @RequestBody TrackPointForm trackPointForm) {
		TrackPoint trackPoint = getTrackPoint(pointUuid, periodUuid, sessionUuid, username);
		formMapper.update(trackPointForm, trackPoint);
		trackPoint = trackPointRepository.save(trackPoint);
		return new ResponseEntity<>(new OperationResponseForm(trackPoint.getId()), HttpStatus.OK);
	}

	private TrackPoint getTrackPoint(UUID pointUuid, UUID periodUuid, UUID sessionUuid, String username) {
		return trackPointRepository.findOneByIdAndTrackPeriodIdAndTrackPeriodSessionUserProfileUsername(pointUuid, periodUuid, username).orElseThrow(() -> new EntityNotFoundException(sessionUuid));
	}
}
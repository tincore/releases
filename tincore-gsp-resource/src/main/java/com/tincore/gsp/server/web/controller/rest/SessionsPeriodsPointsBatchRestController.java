//

package com.tincore.gsp.server.web.controller.rest;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tincore.gsp.server.GspResourceApplication;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.OperationResponseForm;
import com.tincore.gsp.server.form.TrackPointForm;
import com.tincore.gsp.server.service.EntityNotFoundException;
import com.tincore.gsp.server.service.TrackPeriodRepository;
import com.tincore.gsp.server.service.TrackPointRepository;

@RestController
public class SessionsPeriodsPointsBatchRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public TrackPeriodRepository trackPeriodRepository;

	@Autowired
	public TrackPointRepository trackPointRepository;

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/sessions/{session_uuid}/periods/{period_uuid}/points_batch")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPost(@PathVariable("user_name") String username, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@RequestBody List<TrackPointForm> trackPointForm) {
		TrackPeriod trackPeriod = trackPeriodRepository.findByIdAndSessionIdAndSessionUserProfileUsername(periodUuid, sessionUuid, username).orElseThrow(() -> new EntityNotFoundException(periodUuid));
		List<TrackPoint> trackPoints = trackPointForm.stream().map(f -> {
			TrackPoint trackPoint = formMapper.toTrackPoint(f);
			trackPoint.setTrackPeriod(trackPeriod);
			return trackPoint;
		}).collect(Collectors.toList());

		Iterable<TrackPoint> result = trackPointRepository.save(trackPoints);
		List<UUID> uuids = StreamSupport.stream(result.spliterator(), false).map(f -> f.getId()).collect(Collectors.toList());
		return new ResponseEntity<>(new OperationResponseForm(uuids), HttpStatus.CREATED);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/sessions/{session_uuid}/periods/{period_uuid}/points_batch")
	public ResponseEntity<?> doPost(Principal principal, @PathVariable("session_uuid") UUID sessionUuid, @PathVariable("period_uuid") UUID periodUuid,
			@RequestBody List<TrackPointForm> trackPointForm) {
		return doPost(principal.getName(), sessionUuid, periodUuid, trackPointForm);
	}

}
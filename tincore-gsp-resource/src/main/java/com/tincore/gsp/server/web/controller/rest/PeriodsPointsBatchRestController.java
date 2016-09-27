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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.tincore.gsp.server.service.UserProfileService;

@RestController
public class PeriodsPointsBatchRestController implements GpsServerRestController {

	@Autowired
	public FormMapper formMapper;

	@Autowired
	public TrackPeriodRepository trackPeriodRepository;

	@Autowired
	public TrackPointRepository trackPointRepository;

	@Autowired
	public UserProfileService userService;

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/periods/{period_uuid}/points_batch")
	public ResponseEntity<?> doDeleteTrackPointBatch(Principal principal, @PathVariable("period_uuid") UUID periodUuid) {
		return doDeleteTrackPointBatch(principal.getName(), periodUuid);
	}

	@DeleteMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods/{period_uuid}/points_batch")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doDeleteTrackPointBatch(@PathVariable("user_name") String username, @PathVariable("period_uuid") UUID periodUuid) {

		TrackPeriod trackPeriod = trackPeriodRepository.findByIdAndSessionUserProfileUsername(periodUuid, username).orElseThrow(() -> new EntityNotFoundException(periodUuid));

		trackPointRepository.deleteByTrackPeriod(trackPeriod);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/periods/{period_uuid}/points_batch")
	public ResponseEntity<?> doPostTrackPointBatch(Principal principal, @PathVariable("period_uuid") UUID periodUuid, @RequestBody List<TrackPointForm> trackPointForm) {
		return doPostTrackPointBatch(principal.getName(), periodUuid, trackPointForm);
	}

	@PostMapping(GspResourceApplication.API_PREFIX + "/profiles/{user_name}/periods/{period_uuid}/points_batch")
	@PreAuthorize(HAS_ROLE_ROLE_ADMIN_OR_USERNAME_AUTHENTICATION_NAME)
	public ResponseEntity<?> doPostTrackPointBatch(@PathVariable("user_name") String username, @PathVariable("period_uuid") UUID periodUuid, @RequestBody List<TrackPointForm> trackPointForm) {

		TrackPeriod trackPeriod = trackPeriodRepository.findByIdAndSessionUserProfileUsername(periodUuid, username).orElseThrow(() -> new EntityNotFoundException(periodUuid));

		List<TrackPoint> trackPoints = trackPointForm.stream().map(f -> {
			TrackPoint trackPoint = formMapper.toTrackPoint(f);
			trackPoint.setTrackPeriod(trackPeriod);
			return trackPoint;
		}).collect(Collectors.toList());

		Iterable<TrackPoint> result = trackPointRepository.save(trackPoints);
		List<UUID> uuids = StreamSupport.stream(result.spliterator(), false).map(f -> f.getId()).collect(Collectors.toList());

		return new ResponseEntity<>(new OperationResponseForm(uuids), HttpStatus.CREATED);
	}

}
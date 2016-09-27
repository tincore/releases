package com.tincore.gsp.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.gsp.server.domain.SessionGlobal;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.TrackPeriodExtendedForm;
import com.tincore.gsp.server.form.TrackPeriodForm;

public class TrackPeriodRepositoryImpl extends EntityGlobalRepositoryImpl<TrackPeriod> implements TrackPeriodRepositoryExtended {

	@Autowired
	private TrackPeriodRepository trackPeriodRepository;

	@Autowired
	private SessionGlobalRepository sessionGlobalRepository;

	@Autowired
	private TrackPointRepository trackPointRepository;

	
	@Autowired
	FormMapper formMapper;

	// Attention to this. Merge never deletes if false so on optimized (sparse) global changes it may not remove intermediate points
	// but if always clear then it would be possible that empty stats from a second client would wipe server data.
	private boolean clearOnUpdate = false;

	@Override
	public TrackPeriod getByIdAndSessionUserProfileUsername(String username, UUID uuid) {
		return trackPeriodRepository.findByIdAndSessionUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
	}

	@Override
	@Transactional
	public TrackPeriod save(TrackPeriodForm trackPeriodForm, String username) {

		SessionGlobal sessionGlobal = sessionGlobalRepository.getByUsername(username);
		Date dateStart = trackPeriodForm.getDateStart();
		Optional<TrackPeriod> trackPeriodWithSameDate = trackPeriodRepository.findByDateStartAndSessionId(dateStart, sessionGlobal.getId());
		if (trackPeriodWithSameDate.isPresent()) {
			TrackPeriod trackPeriod = trackPeriodWithSameDate.get();
			formMapper.update(trackPeriodForm, trackPeriod);
			return trackPeriodRepository.save(trackPeriod);
		} else {
			TrackPeriod trackPeriod = formMapper.toTrackPeriod(trackPeriodForm);
			trackPeriod.setSession(sessionGlobal);
			return trackPeriodRepository.save(trackPeriod);
		}
	}

	@Override
	@Transactional
	public TrackPeriod saveExtended(TrackPeriodExtendedForm trackPeriodForm, String username) {
		SessionGlobal sessionGlobal = sessionGlobalRepository.getByUsername(username);
		// Check if there is one with same date and return that one!!

		Optional<TrackPeriod> trackPeriodWithSameDate = trackPeriodRepository.findByDateStartAndSessionId(trackPeriodForm.getDateStart(), sessionGlobal.getId());
		if (trackPeriodWithSameDate.isPresent()) {
			return updateExtended(trackPeriodForm, trackPeriodWithSameDate.get());
		} else {
			TrackPeriod trackPeriod = formMapper.toTrackPeriod(trackPeriodForm);
			trackPeriod.setSession(sessionGlobal);
			trackPeriod.ensureChildEntitiesRelations();
			
			checkTrackPointUniqueDates(trackPeriod);	
			
			
			return trackPeriodRepository.save(trackPeriod);
		}
	}

	// fix for some problems with older versions of the client
	private void checkTrackPointUniqueDates(TrackPeriod trackPeriod) {
		if (trackPeriod.getTrackPoints() == null){
			return;
		}
		
		Map<Date, TrackPoint> points = new HashMap<>();
		for (TrackPoint trackPoint: trackPeriod.getTrackPoints()) {
			TrackPoint existingPoint = points.get(trackPoint.getDate());
			if (existingPoint!= null){
				trackPoint.mergePassive(existingPoint);
			}
			points.put(trackPoint.getDate(), trackPoint);
			
		}
	}

	private TrackPeriod updateExtended(TrackPeriodExtendedForm trackPeriodForm, TrackPeriod trackPeriod) {
		if (clearOnUpdate) {
			formMapper.update(trackPeriodForm, trackPeriod);
		} else {
			formMapper.updateWithoutPoints(trackPeriodForm, trackPeriod);
			if (trackPeriod.getTrackPoints() == null) {
				if (trackPeriodForm.getTrackPoints() != null) {
					trackPeriod.setTrackPoints(trackPeriodForm.getTrackPoints().stream().map(t -> formMapper.toTrackPoint(t)).collect(Collectors.toList()));
				}
			} else {
//				Map<Date, TrackPoint> targets = trackPeriod.getTrackPoints().stream().collect(Collectors.toMap(p -> p.getDate(), p -> p));
				Map<Date, TrackPoint> targets = new HashMap<>();
				Set<TrackPoint> dateDupes =new HashSet<>();	
				for (TrackPoint trackPoint: trackPeriod.getTrackPoints()) {
					TrackPoint existingPoint = targets.get(trackPoint.getDate());
					if (existingPoint!= null){
						dateDupes.add(existingPoint);
						trackPoint.mergePassive(existingPoint);
					}
					targets.put(trackPoint.getDate(), trackPoint);
				}

				trackPeriodForm.getTrackPoints().stream().forEach(trackPointForm -> {
					TrackPoint target = targets.get(trackPointForm.getDate());
					if (target != null) {
						formMapper.update(trackPointForm, target);
					} else {
						TrackPoint trackPoint = formMapper.toTrackPoint(trackPointForm);
						trackPoint.setTrackPeriod(trackPeriod);
						trackPeriod.getTrackPoints().add(trackPointRepository.save(trackPoint));
					}
				});
				
				trackPeriod.getTrackPoints().removeAll(dateDupes);

			}
		}
		
//		trackPeriodRepository.flush();

		trackPeriod.ensureChildEntitiesRelations();
		return trackPeriodRepository.save(trackPeriod);
	}

	@Override
	@Transactional
	public TrackPeriod saveExtended(UUID uuid, TrackPeriodExtendedForm trackPeriodForm, String username) {
		return updateExtended(trackPeriodForm, getByIdAndSessionUserProfileUsername(username, uuid));
	}
}

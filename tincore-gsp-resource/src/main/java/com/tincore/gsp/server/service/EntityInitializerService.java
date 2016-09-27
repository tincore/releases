package com.tincore.gsp.server.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tincore.gsp.server.domain.AbstractSession;
import com.tincore.gsp.server.domain.BodyMetric;
import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.Nutrition;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.domain.UserProfile;

@Component
public class EntityInitializerService {

	@Value("${debug}")
	private boolean debug;

	@Autowired
	protected UserProfileService userProfileService;

	@Autowired
	protected UserProfileRepository userProfileRepository;

	@Autowired
	public EquipmentRepository equipmentRepository;

	@Autowired
	public NutritionRepository nutritionRepository;

	@Autowired
	public BodyMetricRepository bodyMetricRepository;


	@Autowired
	public SessionRepository sessionRepository;

	@Autowired
	public TrackPeriodRepository trackPeriodRepository;

	@Autowired
	public TrackPointRepository trackPointRepository;

	public BodyMetric createBodyMetric(Date date, UserProfile userProfile, double weight) {
		return bodyMetricRepository.save(new BodyMetric(date, userProfile, weight));
	}

	public Equipment createEquipment(String name, UserProfile userProfile) {
		return equipmentRepository.save(new Equipment(name, userProfile));
	}

	public Nutrition createNutrition(Date date, UserProfile userProfile) {
		return nutritionRepository.save(new Nutrition(new Date(), userProfile));
	}

	@Transactional
	public Session createSession(UserProfile userProfile) {
		return createSessions(1, userProfile, null).get(0);
	}

	@Transactional
	public Session createSession(UserProfile userProfile, Equipment equipment) {
		return createSessions(1, userProfile, equipment).get(0);
	}

	
	@Transactional
	public List<Session> createSessions(int count, UserProfile userProfile, Equipment equipment) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.truncate(new Date(1), Calendar.MILLISECOND));
		return IntStream.range(0, count).mapToObj(i -> {
			calendar.add(Calendar.HOUR, 1);
			Session session = new Session("sess_" + i + "_" + System.nanoTime(), calendar.getTime(), userProfile);
			if (equipment != null){
				session.setEquipment(equipment);
			}
			Session persistedSession = sessionRepository.saveExtended(session);
			createTrackPeriods(calendar, persistedSession, true);
			sessionRepository.flush();
			sessionRepository.refresh(persistedSession);
			return persistedSession;
		}).collect(Collectors.toList());
	}

	public TrackPeriod createTrackPeriod(AbstractSession session) {
		TrackPeriod trackPeriod = new TrackPeriod(session, new Date(), null);
		trackPeriod = trackPeriodRepository.save(trackPeriod);
		return trackPeriod;
	}

	@Transactional
	public List<TrackPeriod> createTrackPeriods(Calendar calendar, AbstractSession session, boolean createTrackPoints) {
		List<TrackPeriod> list = new ArrayList<>();

		IntStream.range(0, 3).forEach(j -> {
			Date dateStart = calendar.getTime();
			calendar.add(Calendar.MINUTE, 1);
			Date dateEnd = calendar.getTime();
			TrackPeriod trackPeriod = new TrackPeriod(session, dateStart, dateEnd);
			trackPeriod = trackPeriodRepository.save(trackPeriod);
			list.add(trackPeriod);
			if (createTrackPoints) {
				trackPeriod.setTrackPoints(createTrackPoints(dateStart, dateEnd, trackPeriod, false));
			}
			trackPeriod = trackPeriodRepository.save(trackPeriod);
		});

		return list;
	}

	public List<TrackPoint> createTrackPoints(Date dateStart, Date dateEnd, TrackPeriod trackPeriod, boolean save) {
		List<TrackPoint> list = new ArrayList<>();

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dateStart);
		while (cal2.getTime().before(dateEnd)) {
			TrackPoint trackPoint = new TrackPoint(trackPeriod, cal2.getTime());
			if (trackPeriod.getId() != null && save){
				trackPoint = trackPointRepository.save(trackPoint);
			}
			cal2.add(Calendar.SECOND, 5);
			list.add(trackPoint);
		}

		return list;
	}
	
	@Transactional
	public void createDefaultEntitities() {
		if (debug) {
			userProfileService.getOrCreateUserProfile("user");
			
			userProfileService.delete("test");
			UserProfile userProfile = userProfileService.getOrCreateUserProfile("test");
			createSessions(5, userProfile, null);
		}
	}
}

package com.tincore.gsp.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tincore.gsp.server.domain.UserProfile;

@Service
// http://192.168.0.138:9999/uaa/oauth/token {code=0FU9X3, grant_type=authorization_code, redirect_uri=http://tincore.com/fs, scope=openid, client_id=gliderun, client_secret=gliderun}
// curl http://192.168.0.138:9999/uaa/oauth/token -d code=0FU9X3 -d grant_type=authorization_code -d redirect_uri=http://tincore.com/fs -d scope=openid -d client_id=gliderun -d client_secret=gliderun
// curl gliderun:gliderunsecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=test -dpassword=test
// curl gliderun:gliderunsecret@localhost:9999/uaa/oauth/token -d grant_type=clientcredentials
public class UserProfileServiceImpl implements UserProfileService {

	public final static long DEFAULT_WEIGHT = 60000;

	public final static long DEFAULT_SLEEP_DAY_TARGET = 8 * 60 * 60 * 1000;

	public final static double PEDOMETER_STRIDE_DEFAULT_WALK = .9; // 1m

	public final static double PEDOMETER_STRIDE_DEFAULT_RUN = 1.1; // 1m

	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	NutritionRepository nutritionRepository;

	@Autowired
	BodyMetricRepository bodyMetricRepository;

	@Autowired
	EquipmentRepository equipmentRepository;

	@Autowired
	SessionGlobalRepository sessionGlobalRepository;

	@Autowired
	SessionRepository sessionRepository;

	
	@Override
	@Transactional
	public UserProfile create(String username) {
		userProfileRepository.findByUsername(username).ifPresent(user -> {
			throw new UserInvalidException(username);
		});

		UserProfile userProfile = new UserProfile();
		userProfile.setUsername(username);
		UserProfile savedUserProfile = userProfileRepository.save(userProfile);
		sessionGlobalRepository.create(savedUserProfile);

		return savedUserProfile;
	}

	@Override
	@Transactional
	public void delete(String username) {
		userProfileRepository.findByUsername(username).ifPresent(userProfile -> {
			sessionGlobalRepository.deleteByUserProfile(userProfile);
			sessionRepository.deleteByUserProfile(userProfile);

			nutritionRepository.deleteByUserProfile(userProfile);
			bodyMetricRepository.deleteByUserProfile(userProfile);
			equipmentRepository.deleteByUserProfile(userProfile);

			userProfileRepository.delete(userProfile);
		});
	}

	public int getAge(Date date) {
		if (date == null) {
			return 0;
		}

		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar birthDay = new GregorianCalendar();
		GregorianCalendar birthDayThisYear = new GregorianCalendar();

		birthDay.setTime(date);
		birthDayThisYear.setTime(date);
		birthDayThisYear.set(Calendar.YEAR, today.get(Calendar.YEAR));

		int age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

		if (today.getTimeInMillis() < birthDayThisYear.getTimeInMillis()) {
			age--;
		}

		return age;
	}

	public int getHeartRateMaxDefault(Date date) {
		return getHeartRateMaxDefault(getAge(date));
	}

	public int getHeartRateMaxDefault(int age) {
		// classic
		// return age > 0 ? (int) (220 - age)) : 0;
		// tanaka
		return age > 0 ? (int) (208 - (0.7 * age)) : 0;
	}

	public int getHeartRateRest(int heartRateRest) {
		if (heartRateRest > 20) {
			return heartRateRest;
		}
		return getHeartRateRestDefault();
	}

	public int getHeartRateRestDefault() {
		return 65;
	}

	@Override
	public UserProfile getOrCreateUserProfile(String username) {
		return userProfileRepository.findByUsername(username).orElseGet(() -> {
			return create(username);
		});
	}

	public double getStrideLengthRunDefault(double height, boolean gender) {
		if (height > 0) {
			if (gender) {
				return height * 0.667;
			} else {
				return height * 0.665;
			}
		}
		return PEDOMETER_STRIDE_DEFAULT_RUN;
	}

	public double getStrideLengthWalkDefault(double height, boolean gender) {
		if (height > 0) {
			if (gender) {
				return height * 0.415;
			} else {
				return height * 0.413;
			}
		}

		return PEDOMETER_STRIDE_DEFAULT_WALK;
	}

	@Override
	public UserProfile getUserProfile(String username) {
		return this.userProfileRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}

	public double getVolumeOxygenMaxDefault(int heartRateRest, int heartRateMax) {
		if (heartRateRest > 0) {
			int mHr = heartRateMax;
			if (mHr > 0) {
				return 15.3 * ((double) mHr / heartRateRest);
			}
		}
		return 0;
	}

}
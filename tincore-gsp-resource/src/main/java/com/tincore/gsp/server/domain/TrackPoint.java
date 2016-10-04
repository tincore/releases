package com.tincore.gsp.server.domain;


import static com.tincore.gsp.server.domain.Constants.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(indexes = { @Index(name = "trpo_date_idx", columnList = "date", unique = false) })
public class TrackPoint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column
	private long timeDelta;
	@Column
	private boolean moving;
	// ---
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private String comment;
	// ---
	@Column
	private long locationTime;
	@Column
	private double latitude = LOCATION_UNKNOWN;
	@Column
	private double longitude = LOCATION_UNKNOWN;
	@Column
	private double altitude = ALTITUDE_UNKNOWN;
	@Column
	private double locationAccuracy = LOCATION_UNKNOWN;
	// ---
	@Column
	private double altitudeDelta;
	// ---
	@Column
	private double bearing = BEARING_UNKNOWN;
	@Column
	private double tilt = TILT_UNKNOWN;
	// ---
	@Column
	public double locationDistanceDelta;
	@Column
	public double locationSpeed = SPEED_UNKNOWN;
	@Column
	public long pedometerStepDelta = Long.MIN_VALUE;
	@Column
	public double pedometerCadence = CADENCE_UNKNOWN;

	@Column
	private int pedometerGroundContactTime = UNKNOWN_INT;

	@Column
	private double pedometerVerticalOscillation = UNKNOWN_DOUBLE;

	// ---
	@Column
	private double crankCadence = CADENCE_UNKNOWN;

	@Column
	private int crankRevolutionDelta = 0;

	@Column
	private double wheelCadence = CADENCE_UNKNOWN;

	@Column
	private int wheelRevolutionDelta = 0;

	// ---
	@Column
	private double power = POWER_UNKNOWN;

	// ---
	@Column
	private int activityLevel = ACTIVITY_LEVEL_UNKNOWN;

	@Column
	private int activityIntensity = ACTIVITY_LEVEL_UNKNOWN;
	// ---
	@Column
	private int heartRate = UNKNOWN_INT;
	// ---
	@Column
	private double temperature = UNKNOWN_DOUBLE;
	// ---
	@Column
	private double glucose = UNKNOWN_DOUBLE;
	@Column
	private double bloodPressureSystolic = UNKNOWN_DOUBLE;
	@Column
	private double bloodPressureDiastolic = UNKNOWN_DOUBLE;
	// ---
	@Column
	private int lightIntensity;
	// ---
	@Column
	private int sleepDetectSuccess;
	@Column
	private int sleepDetectMove;
	@Column
	private int sleepDetectSnore;
	@JsonIgnore
	@ManyToOne(optional=false)
	private TrackPeriod trackPeriod;
	@Column
	public double caloriesDelta;
	public TrackPoint() {
	}
	public TrackPoint(TrackPeriod trackPeriod, Date date) {
		this.trackPeriod = trackPeriod;
		this.date = date;
	}
	public int getActivityIntensity() {
		return activityIntensity;
	}
	public int getActivityLevel() {
		return activityLevel;
	}

	public double getAltitude() {
		return altitude;
	}

	public double getAltitudeDelta() {
		return altitudeDelta;
	}

	public double getBearing() {
		return bearing;
	}

	public double getBloodPressureDiastolic() {
		return bloodPressureDiastolic;
	}

	public double getBloodPressureSystolic() {
		return bloodPressureSystolic;
	}
	
	public double getCaloriesDelta() {
		return caloriesDelta;
	}

	
	public String getComment() {
		return comment;
	}

	public double getCrankCadence() {
		return crankCadence;
	}

	public int getCrankRevolutionDelta() {
		return crankRevolutionDelta;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getGlucose() {
		return glucose;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public UUID getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public int getLightIntensity() {
		return lightIntensity;
	}

	public double getLocationAccuracy() {
		return locationAccuracy;
	}

	public double getLocationDistanceDelta() {
		return locationDistanceDelta;
	}

	public double getLocationSpeed() {
		return locationSpeed;
	}

	public long getLocationTime() {
		return locationTime;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public double getPedometerCadence() {
		return pedometerCadence;
	}

	public int getPedometerGroundContactTime() {
		return pedometerGroundContactTime;
	}

	public long getPedometerStepDelta() {
		return pedometerStepDelta;
	}

	public double getPedometerVerticalOscillation() {
		return pedometerVerticalOscillation;
	}

	public double getPower() {
		return power;
	}

	public int getSleepDetectMove() {
		return sleepDetectMove;
	}

	public int getSleepDetectSnore() {
		return sleepDetectSnore;
	}

	public int getSleepDetectSuccess() {
		return sleepDetectSuccess;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getTilt() {
		return tilt;
	}

	public long getTimeDelta() {
		return timeDelta;
	}

	public TrackPeriod getTrackPeriod() {
		return trackPeriod;
	}

	public double getWheelCadence() {
		return wheelCadence;
	}

	public int getWheelRevolutionDelta() {
		return wheelRevolutionDelta;
	}

	public boolean isMoving() {
		return moving;
	}

	public void mergePassive(TrackPoint trackPoint) {
		moving = moving || trackPoint.moving;
		caloriesDelta = Math.max(caloriesDelta, trackPoint.caloriesDelta);

		if (activityIntensity == ACTIVITY_LEVEL_UNKNOWN) {
			timeDelta = trackPoint.timeDelta;
			activityIntensity = trackPoint.activityIntensity;
		}
		if (activityLevel == ACTIVITY_LEVEL_UNKNOWN) {
			timeDelta = trackPoint.timeDelta;
			activityLevel = trackPoint.activityLevel;
		}
		if (pedometerStepDelta == 0) {
			timeDelta = trackPoint.timeDelta;
			pedometerStepDelta = trackPoint.pedometerStepDelta;
			caloriesDelta = trackPoint.caloriesDelta;
		}
		if (pedometerCadence== CADENCE_UNKNOWN) {
			pedometerCadence = trackPoint.pedometerCadence;
		}
		if (pedometerGroundContactTime== UNKNOWN_INT) {
			pedometerGroundContactTime = trackPoint.pedometerGroundContactTime;
		}
		if (pedometerVerticalOscillation== UNKNOWN_DOUBLE) {
			pedometerVerticalOscillation = trackPoint.pedometerVerticalOscillation;
		}

		if (trackPoint.description != null) {
			description = (description != null ? description : "") + trackPoint.description;
		}
		if (!(heartRate >= 0)) {
			heartRate = trackPoint.heartRate;
		}
		if (!(latitude != LOCATION_UNKNOWN)) {
			latitude = trackPoint.latitude;
			longitude = trackPoint.longitude;
			altitude = trackPoint.altitude;
			locationAccuracy = trackPoint.locationAccuracy;
			locationTime = trackPoint.locationTime;
			locationDistanceDelta = trackPoint.locationDistanceDelta;
		}
		if (locationSpeed == SPEED_UNKNOWN) {
			locationSpeed = trackPoint.locationSpeed;
		}
		if (altitudeDelta == 0) {
			altitudeDelta = trackPoint.altitudeDelta;
		}
		if (bearing == BEARING_UNKNOWN) {
			bearing = trackPoint.bearing;
		}
		if (tilt == TILT_UNKNOWN) {
			tilt = trackPoint.tilt;
		}

		if (crankCadence == CADENCE_UNKNOWN) {
			crankCadence = trackPoint.crankCadence;
		}
		if (wheelRevolutionDelta == 0) {
			wheelRevolutionDelta = trackPoint.wheelRevolutionDelta;
		}

		if (wheelCadence == CADENCE_UNKNOWN) {
			wheelCadence = trackPoint.wheelCadence;
		}
		if (wheelRevolutionDelta == 0) {
			wheelRevolutionDelta = trackPoint.wheelRevolutionDelta;
		}

		if (power == POWER_UNKNOWN) {
			power = trackPoint.power;
		}

		if (temperature == UNKNOWN_DOUBLE) {
			temperature = trackPoint.temperature;
		}
		if (glucose == UNKNOWN_DOUBLE) {
			glucose = trackPoint.glucose;
		}
		if (bloodPressureSystolic == UNKNOWN_DOUBLE) {
			bloodPressureSystolic = trackPoint.bloodPressureSystolic;
		}
		if (bloodPressureDiastolic == UNKNOWN_DOUBLE) {
			bloodPressureDiastolic = trackPoint.bloodPressureDiastolic;
		}

		if (lightIntensity == 0) {
			lightIntensity = trackPoint.lightIntensity;
		}
		if (sleepDetectSuccess == 0) {
			sleepDetectSuccess = trackPoint.sleepDetectSuccess;
		}
		if (sleepDetectMove == 0) {
			sleepDetectMove = trackPoint.sleepDetectMove;
		}
		if (sleepDetectSnore == 0) {
			sleepDetectSnore = trackPoint.sleepDetectSnore;
		}
	}

	public void setActivityIntensity(int activityIntensity) {
		this.activityIntensity = activityIntensity;
	}

	public void setActivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public void setAltitudeDelta(double altitudeDelta) {
		this.altitudeDelta = altitudeDelta;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public void setBloodPressureDiastolic(double bloodPressureDiastolic) {
		this.bloodPressureDiastolic = bloodPressureDiastolic;
	}

	public void setBloodPressureSystolic(double bloodPressureSystolic) {
		this.bloodPressureSystolic = bloodPressureSystolic;
	}

	public void setCaloriesDelta(double caloriesDelta) {
		this.caloriesDelta = caloriesDelta;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCrankCadence(double crankCadence) {
		this.crankCadence = crankCadence;
	}

	public void setCrankRevolutionDelta(int crankRevolutionDelta) {
		this.crankRevolutionDelta = crankRevolutionDelta;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGlucose(double glucose) {
		this.glucose = glucose;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLightIntensity(int lightIntensity) {
		this.lightIntensity = lightIntensity;
	}

	public void setLocationAccuracy(double locationAccuracy) {
		this.locationAccuracy = locationAccuracy;
	}

	public void setLocationDistanceDelta(double locationDistanceDelta) {
		this.locationDistanceDelta = locationDistanceDelta;
	}

	public void setLocationSpeed(double locationSpeed) {
		this.locationSpeed = locationSpeed;
	}

	public void setLocationTime(long locationTime) {
		this.locationTime = locationTime;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPedometerCadence(double pedometerCadence) {
		this.pedometerCadence = pedometerCadence;
	}

	public void setPedometerGroundContactTime(int pedometerGroundContactTime) {
		this.pedometerGroundContactTime = pedometerGroundContactTime;
	}

	public void setPedometerStepDelta(long pedometerStepDelta) {
		this.pedometerStepDelta = pedometerStepDelta;
	}

	public void setPedometerVerticalOscillation(double pedometerVerticalOscillation) {
		this.pedometerVerticalOscillation = pedometerVerticalOscillation;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public void setSleepDetectMove(int sleepDetectMove) {
		this.sleepDetectMove = sleepDetectMove;
	}

	public void setSleepDetectSnore(int sleepDetectSnore) {
		this.sleepDetectSnore = sleepDetectSnore;
	}

	public void setSleepDetectSuccess(int sleepDetectSuccess) {
		this.sleepDetectSuccess = sleepDetectSuccess;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public void setTilt(double tilt) {
		this.tilt = tilt;
	}

	public void setTimeDelta(long timeDelta) {
		this.timeDelta = timeDelta;
	}

	public void setTrackPeriod(TrackPeriod trackPeriod) {
		this.trackPeriod = trackPeriod;
	}

	public void setWheelCadence(double wheelCadence) {
		this.wheelCadence = wheelCadence;
	}

	public void setWheelRevolutionDelta(int wheelRevolutionDelta) {
		this.wheelRevolutionDelta = wheelRevolutionDelta;
	}

	@Override
	public String toString() {
		return String.format("%s[%s, %s]", getClass().getSimpleName(), id, date);
	}
}

package com.tincore.gsp.server.form;

import static com.tincore.gsp.server.domain.Constants.CADENCE_UNKNOWN;
import static com.tincore.gsp.server.domain.Constants.SPEED_UNKNOWN;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;

public class TrackPointForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private Date date;

	private long timeDelta;

	private boolean moving;

	private String name;

	private String description;

	private String comment;
	// ---

	private long locationTime;

	private double latitude;

	private double longitude;

	private double altitude;

	private double locationAccuracy;
	// ---

	private double altitudeDelta;
	// ---

	private double bearing;

	private double tilt;
	// ---
	
	public double locationDistanceDelta;

	public double locationSpeed = SPEED_UNKNOWN;

	public long pedometerStepDelta = Long.MIN_VALUE;

	public double pedometerCadence = CADENCE_UNKNOWN;

	private int pedometerGroundContactTime;

	private double pedometerVerticalOscillation;
	// ---

	private double crankCadence;

	private int crankRevolutionDelta = 0;

	private double wheelCadence;

	private int wheelRevolutionDelta = 0;
	// ---
	
	private double power;
	// ---

	private int activityLevel;

	private int activityIntensity;
	// ---

	private int heartRate;
	// ---

	private double temperature;
	// ---

	private double glucose;

	private double bloodPressureSystolic;

	private double bloodPressureDiastolic;

	private int lightIntensity;
	// ---

	private int sleepDetectSuccess;

	private int sleepDetectMove;

	private int sleepDetectSnore;

	public double caloriesDelta;

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

	public double getWheelCadence() {
		return wheelCadence;
	}

	public int getWheelRevolutionDelta() {
		return wheelRevolutionDelta;
	}

	public boolean isMoving() {
		return moving;
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

	// ---

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

	public void setWheelCadence(double wheelCadence) {
		this.wheelCadence = wheelCadence;
	}

	public void setWheelRevolutionDelta(int wheelRevolutionDelta) {
		this.wheelRevolutionDelta = wheelRevolutionDelta;
	}

}

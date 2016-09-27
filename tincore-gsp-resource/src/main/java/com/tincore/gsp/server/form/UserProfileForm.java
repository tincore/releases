package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tincore.gsp.server.domain.Gender;

public class UserProfileForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private Gender gender;

	private double height;

	private double strideLength;

	private double strideLengthRun;

	private double pedometerDayDistanceTarget;

	private long pedometerDayStepTarget;

	private double calorieDayTarget;

	private double hydrationDayTarget;

	private double calorieInDayTarget;

	private long sleepDayTarget;

	private int heartRateMax;

	private int heartRateRest;

	private double volumeOxygenMax;

	private long businessVersion;

	public Date getBirthDate() {
		return birthDate;
	}

	public long getBusinessVersion() {
		return businessVersion;
	}

	public double getCalorieDayTarget() {
		return calorieDayTarget;
	}

	public double getCalorieInDayTarget() {
		return calorieInDayTarget;
	}

	public Gender getGender() {
		return gender;
	}

	public int getHeartRateMax() {
		return heartRateMax;
	}

	public int getHeartRateRest() {
		return heartRateRest;
	}

	public double getHeight() {
		return height;
	}

	public double getHydrationDayTarget() {
		return hydrationDayTarget;
	}

	public double getPedometerDayDistanceTarget() {
		return pedometerDayDistanceTarget;
	}

	public long getPedometerDayStepTarget() {
		return pedometerDayStepTarget;
	}

	public long getSleepDayTarget() {
		return sleepDayTarget;
	}

	public double getStrideLength() {
		return strideLength;
	}

	public double getStrideLengthRun() {
		return strideLengthRun;
	}

	public double getVolumeOxygenMax() {
		return volumeOxygenMax;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}

	public void setCalorieDayTarget(double calorieDayTarget) {
		this.calorieDayTarget = calorieDayTarget;
	}

	public void setCalorieInDayTarget(double calorieInDayTarget) {
		this.calorieInDayTarget = calorieInDayTarget;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setHeartRateMax(int heartRateMax) {
		this.heartRateMax = heartRateMax;
	}

	public void setHeartRateRest(int heartRateRest) {
		this.heartRateRest = heartRateRest;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setHydrationDayTarget(double hydrationDayTarget) {
		this.hydrationDayTarget = hydrationDayTarget;
	}

	public void setPedometerDayDistanceTarget(double pedometerDayDistanceTarget) {
		this.pedometerDayDistanceTarget = pedometerDayDistanceTarget;
	}

	public void setPedometerDayStepTarget(long pedometerDayStepTarget) {
		this.pedometerDayStepTarget = pedometerDayStepTarget;
	}

	public void setSleepDayTarget(long sleepDayTarget) {
		this.sleepDayTarget = sleepDayTarget;
	}

	public void setStrideLength(double strideLength) {
		this.strideLength = strideLength;
	}

	public void setStrideLengthRun(double strideLengthRun) {
		this.strideLengthRun = strideLengthRun;
	}

	public void setVolumeOxygenMax(double volumeOxygenMax) {
		this.volumeOxygenMax = volumeOxygenMax;
	}


}

package com.tincore.gsp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;

	@Column(nullable = false)
	private String username;

	@Column
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Column
	private Gender gender;

	@Column
	private double height;

	@Column
	private double strideLength;

	@Column
	private double strideLengthRun;

	@Column
	private double pedometerDayDistanceTarget;

	@Column
	private long pedometerDayStepTarget;

	@Column
	private double calorieDayTarget;

	@Column
	private double hydrationDayTarget;

	@Column
	private double calorieInDayTarget;

	@Column
	private long sleepDayTarget;

	@Column
	private int heartRateMax;

	@Column
	private int heartRateRest;

	@Column
	private double volumeOxygenMax;

	@Column
	public long businessVersion;

	
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

	public UUID getId() {
		return id;
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

	public String getUsername() {
		return username;
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

	public void setId(UUID id) {
		this.id = id;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVolumeOxygenMax(double volumeOxygenMax) {
		this.volumeOxygenMax = volumeOxygenMax;
	}

	@Override
	public String toString() {
		return String.format("%s[%s - %s]", getClass().getSimpleName(), id, username);
	}
}

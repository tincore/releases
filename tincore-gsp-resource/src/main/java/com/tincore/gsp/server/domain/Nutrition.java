package com.tincore.gsp.server.domain;

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

@Entity
@Table(indexes = { @Index(name = "nut_date_idx", columnList = "date", unique = false) })
public class Nutrition implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@ManyToOne(optional = false)
	private UserProfile userProfile;

	@Column
	private String description;

	@Column
	private String code;

	@Column
	private double calories;

	@Column
	private double carbohydrates;

	@Column
	private double fat;

	@Column
	private double protein;

	@Column
	private double fiber;

	@Column
	private double sodium;

	@Column
	private double water;

	@Column
	public long businessVersion;

	public Nutrition() {
	}

	
	public Nutrition(Date date) {
		this.date = date;
	}

	public Nutrition(Date date, UserProfile userProfile) {
		this.date= date;
		this.userProfile = userProfile;
	}

	
	public double getCalories() {
		return calories;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public String getCode() {
		return code;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getFat() {
		return fat;
	}

	public double getFiber() {
		return fiber;
	}

	public UUID getId() {
		return id;
	}

	public long getBusinessVersion() {
		return businessVersion;
	}
	
	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public double getProtein() {
		return protein;
	}

	public double getSodium() {
		return sodium;
	}

	public double getWater() {
		return water;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public void setFiber(double fiber) {
		this.fiber = fiber;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public void setSodium(double sodium) {
		this.sodium = sodium;
	}

	public void setWater(double water) {
		this.water = water;
	}
}

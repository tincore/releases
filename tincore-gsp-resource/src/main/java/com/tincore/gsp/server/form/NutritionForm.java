package com.tincore.gsp.server.form;

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


public class NutritionForm  implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private Date date;

	private String description;

	private String code;

	private double calories;

	private double carbohydrates;

	private double fat;

	private double protein;

	private double fiber;

	private double sodium;

	private double water;

	private long lastModified;

	private long businessVersion;

	public long getBusinessVersion() {
		return businessVersion;
	}

	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
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

	public long getLastModified() {
		return lastModified;
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

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
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

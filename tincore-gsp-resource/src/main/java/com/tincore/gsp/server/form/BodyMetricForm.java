package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class BodyMetricForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	public Date date;

	public double weight;

	public double waistPerimeter;

	public double chestPerimeter;

	public double hipPerimeter;

	public double armPerimeter;

	public double thightPerimeter;

	public double calfPerimeter;

	public double neckPerimeter;

	public double forearmPerimeter;

	public double calliperTricepsLength;

	public double calliperThightLength;

	public double calliperWaistLength;

	public double calliperBackLength;

	public double calliperChestLength;

	private long businessVersion;

	public long getBusinessVersion() {
		return businessVersion;
	}

	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}

	public double getArmPerimeter() {
		return armPerimeter;
	}

	public double getCalfPerimeter() {
		return calfPerimeter;
	}

	public double getCalliperBackLength() {
		return calliperBackLength;
	}

	public double getCalliperChestLength() {
		return calliperChestLength;
	}

	public double getCalliperThightLength() {
		return calliperThightLength;
	}

	public double getCalliperTricepsLength() {
		return calliperTricepsLength;
	}

	public double getCalliperWaistLength() {
		return calliperWaistLength;
	}

	public double getChestPerimeter() {
		return chestPerimeter;
	}

	public Date getDate() {
		return date;
	}

	public double getForearmPerimeter() {
		return forearmPerimeter;
	}

	public double getHipPerimeter() {
		return hipPerimeter;
	}

	public UUID getId() {
		return id;
	}

	public double getNeckPerimeter() {
		return neckPerimeter;
	}

	public double getThightPerimeter() {
		return thightPerimeter;
	}

	public double getWaistPerimeter() {
		return waistPerimeter;
	}

	public double getWeight() {
		return weight;
	}

	public void setArmPerimeter(double armPerimeter) {
		this.armPerimeter = armPerimeter;
	}

	public void setCalfPerimeter(double calfPerimeter) {
		this.calfPerimeter = calfPerimeter;
	}

	public void setCalliperBackLength(double calliperBackLength) {
		this.calliperBackLength = calliperBackLength;
	}

	public void setCalliperChestLength(double calliperChestLength) {
		this.calliperChestLength = calliperChestLength;
	}

	public void setCalliperThightLength(double calliperThightLength) {
		this.calliperThightLength = calliperThightLength;
	}

	public void setCalliperTricepsLength(double calliperTricepsLength) {
		this.calliperTricepsLength = calliperTricepsLength;
	}

	public void setCalliperWaistLength(double calliperWaistLength) {
		this.calliperWaistLength = calliperWaistLength;
	}

	public void setChestPerimeter(double chestPerimeter) {
		this.chestPerimeter = chestPerimeter;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setForearmPerimeter(double forearmPerimeter) {
		this.forearmPerimeter = forearmPerimeter;
	}

	public void setHipPerimeter(double hipPerimeter) {
		this.hipPerimeter = hipPerimeter;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setNeckPerimeter(double neckPerimeter) {
		this.neckPerimeter = neckPerimeter;
	}

	public void setThightPerimeter(double thightPerimeter) {
		this.thightPerimeter = thightPerimeter;
	}

	public void setWaistPerimeter(double waistPerimeter) {
		this.waistPerimeter = waistPerimeter;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}

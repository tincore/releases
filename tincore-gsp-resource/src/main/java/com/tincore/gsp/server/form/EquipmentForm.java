package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.UUID;

public class EquipmentForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private String name;

	private double wheelPerimeter;

	private double weight;

	private long businessVersion;

	public long getBusinessVersion() {
		return businessVersion;
	}

	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getWeight() {
		return weight;
	}

	public double getWheelPerimeter() {
		return wheelPerimeter;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setWheelPerimeter(double wheelPerimeter) {
		this.wheelPerimeter = wheelPerimeter;
	}

}

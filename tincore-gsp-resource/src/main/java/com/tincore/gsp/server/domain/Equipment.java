package com.tincore.gsp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;

	@Column
	private String name;

	@Column
	private double wheelPerimeter;

	@Column
	private double weight;

	@ManyToOne(optional = false)
	private UserProfile userProfile;

	@OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY)
	private List<Session> sessions;

	@Column
	public long businessVersion;

	public Equipment() {
	}

	public Equipment(String name) {
		this.name = name;
	}

	public Equipment(String name, UserProfile userProfile) {
		this.name = name;
		this.userProfile = userProfile;
	}

	public Equipment(UUID id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public UserProfile getUserProfile() {
		return userProfile;
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

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public void setUserProfile(UserProfile personalProfile) {
		this.userProfile = personalProfile;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setWheelPerimeter(double wheelPerimeter) {
		this.wheelPerimeter = wheelPerimeter;
	}

}

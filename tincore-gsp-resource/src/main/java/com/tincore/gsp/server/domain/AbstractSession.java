package com.tincore.gsp.server.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Session")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "session_category")
public abstract class AbstractSession {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;

	@JsonIgnore
	@ManyToOne(optional = false)
	private UserProfile userProfile;

	@JsonIgnore
	@OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("dateStart")
	private List<TrackPeriod> trackPeriods;

	public AbstractSession() {
		super();
	}

	public AbstractSession(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public UUID getId() {
		return id;
	}

	public List<TrackPeriod> getTrackPeriods() {
		return trackPeriods;
	}

	// public String getType() {
	// return type;
	// }

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setTrackPeriods(List<TrackPeriod> trackPeriods) {
		this.trackPeriods = trackPeriods;
	}

	// public void setType(String type) {
	// this.type = type;
	// }

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

}
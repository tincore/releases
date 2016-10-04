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

import com.tincore.gsp.util.metric.WeightUnit;

@Entity
@Table(indexes = { @Index(name = "bome_date_idx", columnList = "date", unique = false) })
public class BodyMetric implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;

	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;

	@ManyToOne(optional=false)
	private UserProfile userProfile;

	@Column
	public double weight;

	@Column
	public double waistPerimeter;

	@Column
	public double chestPerimeter;

	@Column
	public double hipPerimeter;

	@Column
	public double armPerimeter;

	@Column
	public double thightPerimeter;

	@Column
	public double calfPerimeter;

	@Column
	public double neckPerimeter;

	@Column
	public double forearmPerimeter;

	@Column
	public double calliperTricepsLength;

	@Column
	public double calliperThightLength;

	@Column
	public double calliperWaistLength;

	@Column
	public double calliperBackLength;

	@Column
	public double calliperChestLength;

	@Column
	public Date fitnesssyncerSyncDate;

	@Column
	public String fitnesssyncerSyncId;

	@Column
	public long businessVersion;

	public BodyMetric() {
	}
	
	public BodyMetric(Date date) {
		this.date = date;
	}

	public BodyMetric(Date date, UserProfile userProfile) {
		this(date);
		this.userProfile = userProfile;
	}

	
	public BodyMetric(Date date, UserProfile userProfile, double weight) {
		this(date, userProfile);
		this.weight = weight;
	}

	public double getArmPerimeter() {
		return armPerimeter;
	}

	public double getBmi() {
		return getBmi(weight);
	}

	public double getBmi(double weight) {
		return userProfile.getHeight() != 0 ? (WeightUnit.convertFromDefault(weight, WeightUnit.Kg)) / Math.pow(userProfile.getHeight(), 2) : 0;
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

	public Date getFitnesssyncerSyncDate() {
		return fitnesssyncerSyncDate;
	}

	public String getFitnesssyncerSyncId() {
		return fitnesssyncerSyncId;
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

	public long getBusinessVersion() {
		return businessVersion;
	}
	
	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}

	public double getNeckPerimeter() {
		return neckPerimeter;
	}

	public UserProfile getUserProfile() {
		return userProfile;
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

	public void resetSync() {
		if (fitnesssyncerSyncDate != null) {
			fitnesssyncerSyncDate = Constants.DATE_DATA_ITEM_RESYNC;
		}
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

	public void setFitnesssyncerSyncDate(Date fitnesssyncerSyncDate) {
		this.fitnesssyncerSyncDate = fitnesssyncerSyncDate;
	}

	public void setFitnesssyncerSyncId(String fitnesssyncerSyncId) {
		this.fitnesssyncerSyncId = fitnesssyncerSyncId;
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

	public void setUserProfile(UserProfile personalProfile) {
		this.userProfile = personalProfile;
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

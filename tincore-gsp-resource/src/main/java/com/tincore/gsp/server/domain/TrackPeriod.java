package com.tincore.gsp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(indexes = { @Index(name = "trpr_date_start_idx", columnList = "dateStart", unique = false) })
public class TrackPeriod implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = Constants.UUID_TYPE)
	private UUID id;

	@ManyToOne(optional = false)
	private AbstractSession session;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateStart;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnd;

	@Column
	private Date sleepOverrideStart;

	@Column
	private Date sleepOverrideEnd;

	@Column
	public long businessVersion;

	@OneToMany(mappedBy = "trackPeriod", cascade = CascadeType.ALL, orphanRemoval=true)
	@OrderBy ("date")
	private List<TrackPoint> trackPoints;

	public TrackPeriod() {
	}

	public TrackPeriod(AbstractSession session, Date dateStart, Date dateEnd) {
		this.session = session;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public TrackPeriod(Session session, Date dateStart) {
		this(session, dateStart, null);
	}
	
	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public UUID getId() {
		return id;
	}

	public AbstractSession getSession() {
		return session;
	}

	public Date getSleepOverrideEnd() {
		return sleepOverrideEnd;
	}

	public Date getSleepOverrideStart() {
		return sleepOverrideStart;
	}

	public long getTimeDelta() {
		return (dateEnd == null || dateStart == null) ? 0 : dateEnd.getTime() - dateStart.getTime();
	}

	public List<TrackPoint> getTrackPoints() {
		return trackPoints;
	}

	public boolean isOpen() {
		return dateEnd == null;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setId(UUID id) {
		this.id = id;
	}


	public long getBusinessVersion() {
		return businessVersion;
	}
	
	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}
	
	public void setSession(AbstractSession session) {
		this.session = session;
	}

	public void setSleepOverrideEnd(Date sleepOverrideEnd) {
		this.sleepOverrideEnd = sleepOverrideEnd;
	}

	public void setSleepOverrideStart(Date sleepOverrideStart) {
		this.sleepOverrideStart = sleepOverrideStart;
	}

	public void setTrackPoints(List<TrackPoint> trackPoints) {
		this.trackPoints = trackPoints;
	}

	@Override
	public String toString() {
		return String.format("%s[%s, %s, %s]", getClass().getSimpleName(), id, dateStart, dateEnd);
	}

	public void ensureChildEntitiesRelations() {
		if (getTrackPoints() != null){
			for (TrackPoint trackPoint : getTrackPoints()) {
				trackPoint.setTrackPeriod(this);
			}
		}
	}
}

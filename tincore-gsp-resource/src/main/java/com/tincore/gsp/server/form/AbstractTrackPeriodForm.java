package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractTrackPeriodForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private Date dateStart;

	private Date dateEnd;

	private Date sleepOverrideStart;

	private Date sleepOverrideEnd;

	private long businessVersion;
	
	public long getBusinessVersion() {
		return businessVersion;
	}
	
	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
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

	public Date getSleepOverrideEnd() {
		return sleepOverrideEnd;
	}

	public Date getSleepOverrideStart() {
		return sleepOverrideStart;
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

	public void setSleepOverrideEnd(Date sleepOverrideEnd) {
		this.sleepOverrideEnd = sleepOverrideEnd;
	}

	public void setSleepOverrideStart(Date sleepOverrideStart) {
		this.sleepOverrideStart = sleepOverrideStart;
	}

}

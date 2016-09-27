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


public class SummaryForm  implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

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

	public void setId(UUID id) {
		this.id = id;
	}


}

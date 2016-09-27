package com.tincore.gsp.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = "G")
public class SessionGlobal extends AbstractSession implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateStart = new Date(0);

}

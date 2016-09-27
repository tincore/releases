package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SessionExtendedForm extends AbstractSessionForm implements Serializable {

	private static final long serialVersionUID = 1L;


	List<TrackPeriodExtendedForm> trackPeriods = new ArrayList<>();
	
	public List<TrackPeriodExtendedForm> getTrackPeriods() {
		return trackPeriods;
	}
	
	public void setTrackPeriods(List<TrackPeriodExtendedForm> trackPeriods) {
		this.trackPeriods = trackPeriods;
	}
	
}

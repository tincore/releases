package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackPeriodExtendedForm extends AbstractTrackPeriodForm implements Serializable {
	// If inherit then map issues
	private static final long serialVersionUID = 1L;

	List<TrackPointForm> trackPoints = new ArrayList<>();

	public List<TrackPointForm> getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(List<TrackPointForm> trackPoints) {
		this.trackPoints = trackPoints;
	}

}

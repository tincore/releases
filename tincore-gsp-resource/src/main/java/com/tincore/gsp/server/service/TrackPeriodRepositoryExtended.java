package com.tincore.gsp.server.service;

import java.util.UUID;

import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.form.TrackPeriodExtendedForm;
import com.tincore.gsp.server.form.TrackPeriodForm;

public interface TrackPeriodRepositoryExtended extends CrudRepositoryExtended<TrackPeriod> {

	TrackPeriod saveExtended(UUID uuid, TrackPeriodExtendedForm periodForm, String username);
	
	TrackPeriod saveExtended(TrackPeriodExtendedForm periodForm, String username);
	
	TrackPeriod getByIdAndSessionUserProfileUsername(String username, UUID uuid);

	TrackPeriod save(TrackPeriodForm trackPeriodForm, String username);
}

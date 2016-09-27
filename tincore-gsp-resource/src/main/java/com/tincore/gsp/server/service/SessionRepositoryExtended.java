package com.tincore.gsp.server.service;

import java.util.UUID;

import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.UserProfile;
import com.tincore.gsp.server.form.SessionExtendedForm;

public interface SessionRepositoryExtended extends CrudRepositoryExtended<Session> {

	Session saveExtended(Session session);

	Session saveExtended(UUID uuid, SessionExtendedForm sessionForm, String username);

}

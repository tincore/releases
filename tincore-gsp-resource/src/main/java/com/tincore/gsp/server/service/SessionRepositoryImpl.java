package com.tincore.gsp.server.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.form.FormMapper;
import com.tincore.gsp.server.form.SessionExtendedForm;

public class SessionRepositoryImpl extends EntityGlobalRepositoryImpl<Session> implements SessionRepositoryExtended {

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private FormMapper formMapper;

	@Override
	@Transactional
	public Session saveExtended(Session session) {
		if (session.getEquipment() != null) {
			session.setEquipment(equipmentRepository.findOneByIdAndUserProfileUsername(session.getEquipment().getId(), session.getUserProfile().getUsername()).orElse(null));
		}

		if (session.getId() == null) {
			session.ensureChildEntitiesRelations();
		}

		return sessionRepository.save(session);
	}

	@Override
	@Transactional
	public Session saveExtended(UUID uuid, SessionExtendedForm sessionForm, String username) {
		Session session = sessionRepository.findOneByIdAndUserProfileUsername(uuid, username).orElseThrow(() -> new EntityNotFoundException(uuid));
		session.getTrackPeriods().clear();
		formMapper.update(sessionForm, session);
		session.ensureChildEntitiesRelations();
		return saveExtended(session);
	}

}

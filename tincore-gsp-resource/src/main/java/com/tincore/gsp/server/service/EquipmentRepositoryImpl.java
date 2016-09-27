package com.tincore.gsp.server.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tincore.gsp.server.domain.Equipment;

public class EquipmentRepositoryImpl extends EntityGlobalRepositoryImpl<Equipment> implements EquipmentRepositoryExtended {

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Transactional
	@Override
	public void deleteFull(Equipment equipment) {
		sessionRepository.updateSetEquipmentNullByEquipment(equipment);
		equipmentRepository.delete(equipment);

	}

}

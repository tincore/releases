package com.tincore.gsp.server.service;

import com.tincore.gsp.server.domain.Equipment;

public interface EquipmentRepositoryExtended extends CrudRepositoryExtended<Equipment> {

	void deleteFull(Equipment equipment);

}

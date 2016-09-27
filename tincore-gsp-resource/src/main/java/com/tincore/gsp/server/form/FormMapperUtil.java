package com.tincore.gsp.server.form;

import java.util.Date;
import java.util.UUID;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.tincore.gsp.server.domain.Equipment;

@Component
public class FormMapperUtil {

	public long map(Date value) {
		return value != null ? value.getTime() : 0;
	}

	public UUID map(Equipment equipment) {
		if (equipment == null) {
			return null;
		}
		return equipment.getId();
	}

	public Date map(long value) {
		return new Date(value);
	}

	//TODO: inject repo and get the real deal?
	public Equipment map(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		return new Equipment(uuid);
	}

	@AfterMapping
	public void onAfterPage(Page<?> page, @MappingTarget PageForm<?> pageForm) {
		pageForm.setSize(page.getSize());
		pageForm.setNumber(page.getNumber());
		pageForm.setTotalElements(page.getTotalElements());
		pageForm.setTotalPages(page.getTotalPages());
	}

}

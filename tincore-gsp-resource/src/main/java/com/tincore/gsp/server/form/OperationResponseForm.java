package com.tincore.gsp.server.form;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OperationResponseForm  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private List<String> ids;

	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OperationResponseForm() {
	}

	public OperationResponseForm(UUID id) {
		this.id = String.valueOf(id);

	
	}

	public OperationResponseForm(String id) {
		this.id = id;
	}

	public OperationResponseForm(List<UUID> ids) {
		this.ids = ids.stream().map(i -> String.valueOf(i)).collect(Collectors.toList());
	}

	
	
}

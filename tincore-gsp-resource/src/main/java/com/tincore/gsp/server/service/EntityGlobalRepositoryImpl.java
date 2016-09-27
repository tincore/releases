package com.tincore.gsp.server.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

public class EntityGlobalRepositoryImpl<T> implements CrudRepositoryExtended<T> {

	@Autowired
	EntityManager entityManager;

	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	
	@Override
	public void refresh(T entity) {
		entityManager.refresh(entity);
	}

}

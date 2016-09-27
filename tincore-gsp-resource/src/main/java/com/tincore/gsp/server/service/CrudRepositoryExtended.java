package com.tincore.gsp.server.service;

public interface CrudRepositoryExtended<T> {

	void refresh(T entity);

	void flush();

	void clear();

}

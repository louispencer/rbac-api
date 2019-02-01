package com.github.rbac.dao;

import java.util.List;

import com.github.rbac.model.ModelEntity;

public interface DAO<T extends ModelEntity> {
	
	List<T> list();
	
	T find(Long id);
	
	Long create(T entity);
	
	void update(T entity);
	
	void remove(Long id);
	
}

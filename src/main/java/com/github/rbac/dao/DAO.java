package com.github.rbac.dao;

import java.util.List;

public interface DAO<T> {
	
	List<T> list();
	
	T find(Long id);
	
	T save(T entity);
	
	void remove(Long id);
	
}

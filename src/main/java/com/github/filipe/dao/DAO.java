package com.github.filipe.dao;

import java.util.List;

public interface DAO<T> {
	
	List<T> list();
	
	List<T> listWithCriteria(List<?> fields);
	
	T find(Long id);
	
	T save(T entity);
	
	void remove(Long id);
	
}

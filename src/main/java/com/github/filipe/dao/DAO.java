package com.github.filipe.dao;

import java.util.List;

public interface DAO<T> {
	
	public List<T> list();
	
	public T find(Long id);
	
	public T save(T entity);
	
	public void remove(Long id);
	
}
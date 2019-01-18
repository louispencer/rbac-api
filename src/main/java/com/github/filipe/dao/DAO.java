package com.github.filipe.dao;

import java.util.Collection;

public interface DAO<T> {
	
	public Collection<T> list();
	
	public Collection<T> list(String namedQuery);
	
	public T find(Long id);
	
	public T save(T entity);
	
	public void remove(Long id);
	
}

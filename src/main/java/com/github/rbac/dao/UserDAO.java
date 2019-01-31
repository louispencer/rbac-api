package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.rbac.model.User;

public class UserDAO extends AbstractDAO<User> implements DAO<User> {
	
	private final List<String> fields = Arrays.asList("id","name","email","registeredIn","active"); 
	
	@Override
	public List<User> list() {
		return listWithCriteria(fields);
	}
	
	@Override
	public User find(Long id) {
		User user = findWithCriteria(fields);
		if (user==null) {
			throw new NotFoundException();
		}
		return user;
	}

}

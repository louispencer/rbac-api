package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import com.github.rbac.model.User;

public class UserDAO extends AbstractDAO<User> implements DAO<User> {
	
	@Override
	public List<User> list() {
		return listWithCriteria(Arrays.asList("id","name","email","registeredIn","active"));
	}
	
	@Override
	public User find(Long id) {
		return findWithGraph(id, "user.graph");
	}

}

package com.github.filipe.dao;

import java.util.Arrays;
import java.util.List;

import com.github.filipe.model.Role;

public class RoleDAO extends AbstractDAO<Role> implements DAO<Role> {
	
	@Override
	public List<Role> list() {
		return listWithCriteria(Arrays.asList("id","description","active"));
	}
	
	@Override
	public Role find(Long id) {
		return findWithGraph(id, "role.graph");
	}

}

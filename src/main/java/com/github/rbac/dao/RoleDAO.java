package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Role;

public class RoleDAO extends AbstractDAO<Role> implements DAO<Role> {
	
	@Override
	public List<Role> list() {
		return listWithCriteria(Arrays.asList("id","description","active"));
	}
	
	@Override
	public Role find(Long id) {
		
		Role role = findWithGraph(id, "role.graph");
		if (role==null) {
			throw new NotFoundException();
		}
		
		return role;
	}

}

package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Role;

public class RoleDAO extends AbstractDAO<Role> implements DAO<Role> {
	
	private final List<String> fields = Arrays.asList("id","description","active");
	
	@Override
	public List<Role> list() {
		return listWithCriteria(fields);
	}
	
	@Override
	public Role find(Long id) {
		
		Role role = findWithCriteria(fields);
		if (role==null) {
			throw new NotFoundException();
		}
		
		return role;
	}

}

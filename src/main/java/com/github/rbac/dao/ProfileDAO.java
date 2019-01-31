package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Profile;

public class ProfileDAO extends AbstractDAO<Profile> implements DAO<Profile> {
	
	private final List<String> fields = Arrays.asList("id","description","active");
	
	@Override
	public List<Profile> list() {
		return listWithCriteria(fields);
	}
	
	@Override
	public Profile find(Long id) {
		
		Profile profile = findWithCriteria(fields);
		if (profile==null) {
			throw new NotFoundException();
		}
		
		return profile;
	}

}

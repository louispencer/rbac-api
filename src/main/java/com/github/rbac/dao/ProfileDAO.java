package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Profile;

public class ProfileDAO extends AbstractDAO<Profile> implements DAO<Profile> {
	
	@Override
	public List<Profile> list() {
		return listWithCriteria(Arrays.asList("id","description","active"));
	}
	
	@Override
	public Profile find(Long id) {
		
		Profile profile = findWithGraph(id, "profile.graph");
		if (profile==null) {
			throw new NotFoundException();
		}
		
		return profile;
	}

}

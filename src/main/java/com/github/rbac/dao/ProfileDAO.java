package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Profile;

public class ProfileDAO extends AbstractDAO<Profile> {
	
	private final List<String> fields = Arrays.asList("id","description","active");
	
	public List<Profile> list(final Long user) {
		restrictions.add(builder.and(root.get("users").in(user)));
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

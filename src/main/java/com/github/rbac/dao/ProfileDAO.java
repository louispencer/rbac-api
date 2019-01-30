package com.github.rbac.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Profile;

public class ProfileDAO extends AbstractDAO<Profile> {
	
	private final List<String> fields = Arrays.asList("id","description","active");
	
	
	public List<Profile> filter(final Long user) {
		List<Predicate> restrictions = new ArrayList<Predicate>();
		if (user!=null && user>0L) {
			restrictions.add(root.join("users").in(user));
		}
		return listWithCriteria(fields, restrictions);
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

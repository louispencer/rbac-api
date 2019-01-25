package com.github.filipe.dao;

import java.util.Arrays;
import java.util.List;

import com.github.filipe.model.Profile;

public class ProfileDAO extends AbstractDAO<Profile> implements DAO<Profile> {
	
	@Override
	public List<Profile> list() {
		return listWithCriteria(Arrays.asList("id","description","active"));
	}
	
	@Override
	public Profile find(Long id) {
		return findWithGraph(id, "profile.graph");
	}

}

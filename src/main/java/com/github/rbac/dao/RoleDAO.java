package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.github.rbac.model.Role;

public class RoleDAO extends AbstractDAO<Role> implements DAO<Role> {

	private final List<String> fields = Arrays.asList("id", "description", "active");

	@Override
	public List<Role> list() {
		return listWithCriteria(fields);
	}

	@Override
	public Role find(Long id) {
		try {
			return findWithCriteria(id, fields);
		} catch (NoResultException e) {
			throw new NotFoundException();
		} catch (NonUniqueResultException e) {
			throw new BadRequestException();
		}
	}

}

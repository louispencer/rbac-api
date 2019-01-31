package com.github.rbac.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.github.rbac.model.User;

public class UserDAO extends AbstractDAO<User> implements DAO<User> {

	private final List<String> fields = Arrays.asList("id", "name", "email", "registeredIn", "active");

	@Override
	public List<User> list() {
		return listWithCriteria(fields);
	}

	@Override
	public User find(Long id) {
		try {
			return findWithCriteria(id, fields);
		} catch (NoResultException e) {
			throw new NotFoundException();
		} catch (NonUniqueResultException e) {
			throw new BadRequestException();
		}
	}

}

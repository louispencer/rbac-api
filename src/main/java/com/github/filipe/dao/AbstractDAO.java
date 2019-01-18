package com.github.filipe.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public abstract class AbstractDAO<T> implements DAO<T> {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	private final Class<T> clazz;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		Type type = getClass().getGenericSuperclass();

		while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != AbstractDAO.class) {
			if (type instanceof ParameterizedType) {
				type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
			} else {
				type = ((Class<?>) type).getGenericSuperclass();
			}
		}

		this.clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		return em.createQuery("select o from " + clazz.getSimpleName() + " o").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(String namedQuery) {
		Query query = em.createNamedQuery(namedQuery);
		return query.getResultList();
	}

	public T find(Long id) {
		
		/*EntityGraph graph = em.getEntityGraph(entityGraph);		
		Map hints = new HashMap();
		hints.put("javax.persistence.fetchgraph", graph);*/
		
		return em.find(clazz, id);
	}

	@Transactional
	public T save(T entity) {
		return em.merge(entity);
	}

	@Transactional
	public void remove(Long id) {
		em.remove(find(id));
	}

}

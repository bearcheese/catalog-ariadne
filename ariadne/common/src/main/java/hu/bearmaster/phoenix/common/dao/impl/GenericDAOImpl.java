package hu.bearmaster.phoenix.common.dao.impl;

import hu.bearmaster.phoenix.common.dao.GenericDAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class GenericDAOImpl<T, ID extends Serializable> extends
		HibernateDaoSupport implements GenericDAO<T, ID> {

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void delete(T entity, Boolean doItNow) {
		if (doItNow) {
			getSession().setFlushMode(FlushMode.ALWAYS);
		}
		delete(entity);
	}

	public int deleteById(ID id) {
		T entity = findByID(id);
		delete(entity);
		return 0;
	}

	public List<T> findAll() {
		// TODO findByCriteria();
		// return (List<T>)
		// getHibernateTemplate().loadAll(getPersistentClass());
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		DetachedCriteria crit = DetachedCriteria.forClass(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return (List<T>) getHibernateTemplate().findByCriteria(crit);
	}

	public T findByID(ID id) {
		return (T) getHibernateTemplate().load(getPersistentClass(), id);
	}

	public T findByID(ID id, boolean lock) {
		if (lock) {
			return (T) getHibernateTemplate().load(getPersistentClass(), id,
					LockMode.UPGRADE);
		} else {
			return findByID(id);
		}
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

}

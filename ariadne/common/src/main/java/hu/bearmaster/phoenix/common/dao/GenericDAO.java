package hu.bearmaster.phoenix.common.dao;

import java.io.Serializable;
import java.util.List;

//FIXME dokumentáció!
public interface GenericDAO<T, ID extends Serializable> {

	T findByID(ID id, boolean lock);

	T findByID(ID id);

	List<T> findAll();

	void save(T entity);

	void update(T entity);

	void saveOrUpdate(T entity);

	void delete(T entity);

	void delete(T entity, Boolean doItNow);

	int deleteById(ID id);
}

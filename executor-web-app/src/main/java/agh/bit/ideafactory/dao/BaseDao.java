package agh.bit.ideafactory.dao;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseDao<T> {

	public T findById(Long id);

	List<T> findAll();

	void deleteById(long id);

	void delete(T object);

	void save(T object);

	void saveOrUpdate(T object);

	void update(T object);

	Criteria getCriteria();

	Criteria getCriteria(Class clazz);

}

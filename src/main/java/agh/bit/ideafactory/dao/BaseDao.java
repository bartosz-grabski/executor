package agh.bit.ideafactory.dao;

import java.util.List;

public interface BaseDao<T> {

	public T findById( Long id);

	List<T> findAll();

	void deleteById(long id);

	void delete(T object);

	void save(T object);
	
}

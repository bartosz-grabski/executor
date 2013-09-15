package agh.bit.ideafactory.daoimpl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import agh.bit.ideafactory.dao.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	protected final Class<T> entityClass;

	public BaseDaoImpl() {
		entityClass = getEntityClass();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		return criteria.list() != null ? (ArrayList<T>) criteria.list() : new ArrayList<T>();
	}

	@Override
	public void save(T object) {

		sessionFactory.getCurrentSession().save(object);

	}

	@Override
	public void saveOrUpdate(T object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);

	}

	@Override
	public void delete(T object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void deleteById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(entityClass);
		criteria.add(Restrictions.eq("id", id));
		Object object = criteria.uniqueResult();
		if (object != null) {
			session.delete(object);
		}
	}

	@Override
	public void update(T object) {
		sessionFactory.getCurrentSession().update(object);

	}

	@Override
	public Criteria getCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(entityClass);
	}

	@Override
	public Criteria getCriteria(Class clazz) {
		return sessionFactory.getCurrentSession().createCriteria(clazz);
	}

}

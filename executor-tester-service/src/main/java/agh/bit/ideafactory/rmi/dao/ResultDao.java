package agh.bit.ideafactory.rmi.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.model.Result;

@Repository("resultDao")
public class ResultDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Serializable save(Result result) {
		return getSession().save(result);
	}

	public Result getResultById(Long id) {
		return (Result) getCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private Criteria getCriteria() {
		return getSession().createCriteria(Result.class);
	}

}

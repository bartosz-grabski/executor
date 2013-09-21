package agh.bit.ideafactory.rmi.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.model.Result;

@Repository("resultDao")
public class ResultDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void putResult(Result result) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Result.class);
	}
	
}

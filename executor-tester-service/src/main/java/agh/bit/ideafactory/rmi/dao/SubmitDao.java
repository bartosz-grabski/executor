package agh.bit.ideafactory.rmi.dao;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.model.Submit;

@Repository("submitDao")
public class SubmitDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Submit getSubmitById(long id) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("id", id));
		crit.setFetchMode("exercise.tests", FetchMode.JOIN);
		return (Submit) crit.uniqueResult();
	}
	
	
	private Criteria getCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(Submit.class);
	}
}

package agh.bit.ideafactory.rmi.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
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
		return (Submit) crit.uniqueResult();
	}
	
	
	private Criteria getCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(Submit.class);
	}
}

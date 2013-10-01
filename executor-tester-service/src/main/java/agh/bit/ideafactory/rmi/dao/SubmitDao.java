package agh.bit.ideafactory.rmi.dao;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.model.Submit;

/**
 * Class for I/O database operations on Submit objects
 * 
 * @author bgrabski
 * 
 */
@Repository("submitDao")
public class SubmitDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Retrieves Submit by id 
	 * 
	 * @param id - id of Submit to be fetched
	 * @return Submit with given id or null if no unique result found
	 */
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

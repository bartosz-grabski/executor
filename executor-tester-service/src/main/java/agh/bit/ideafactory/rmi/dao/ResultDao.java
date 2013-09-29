package agh.bit.ideafactory.rmi.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.model.Result;

/**
 * Class for retrieval and saving of Result objects
 * 
 * @author bgrabski
 * 
 */
@Repository("resultDao")
public class ResultDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Persist result to database
	 * 
	 * @param result - Result to be saved
	 *            
	 * @return - generated identifier
	 */
	public Serializable save(Result result) {
		return getSession().save(result);
	}

	/**
	 * Fetches Result with given id from database
	 * 
	 * @param id  - Id of Result to be fetched
	 *           
	 * @return Result with given id or null if no unique result found
	 */
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

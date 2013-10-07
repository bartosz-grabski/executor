package agh.bit.ideafactory.rmi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Test;

@Repository("testDao")
public class TestDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 * 
	 * @param exerciseId - id of Exercise which tests are to be fetched
	 *            
	 * @return List of test belonging to given Exercise
	 */
	public List<Test> getTestsForExercise(Long exerciseId) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("id", exerciseId));
		crit.setFetchMode("tests", FetchMode.JOIN);
		return ((Exercise) crit.uniqueResult()).getTests();
	}

	private Criteria getCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(Exercise.class);
	}

}

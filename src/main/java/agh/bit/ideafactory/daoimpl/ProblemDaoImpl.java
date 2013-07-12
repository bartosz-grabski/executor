package agh.bit.ideafactory.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

@Repository("problemDao")
public class ProblemDaoImpl extends BaseDaoImpl<Problem> implements ProblemDao{

    @Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addProblem(Problem problem) {
		Session session = sessionFactory.getCurrentSession();
		session.save(problem);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getProblemsByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria crit = session.createCriteria(Problem.class);
		crit.add(Restrictions.eq("user", user));
		return crit.list()!=null? (ArrayList<Problem>) crit.list() : new ArrayList<Problem>();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getProblems() {
	
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Problem.class);
		return crit.list()!=null? (ArrayList<Problem>) crit.list() : new ArrayList<Problem>();
	}

	@Override
	public Problem getById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Problem.class);
		crit.add(Restrictions.eq("id", id));
		return (Problem)crit.uniqueResult();

	}

}

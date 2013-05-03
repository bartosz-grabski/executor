package agh.bit.ideafactory.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

@Repository("problemDao")
public class ProblemDaoImpl implements ProblemDao{

    @Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addProblem(Problem problem) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(problem);
			session.getTransaction().commit();
		}
		catch ( HibernateException e) {
			session.getTransaction().rollback();
			System.out.println("Adding Problem didnt work!");
			e.printStackTrace();
		}
		finally {
			session.close();
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getProblemsByUser(User user) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Criteria crit = session.createCriteria(Problem.class);
			crit.add(Restrictions.eq("user_id", user.getId()));
			session.getTransaction().commit();
			return crit.list()!=null? (ArrayList<Problem>) crit.list() : new ArrayList<Problem>();
		}
		catch ( HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getProblems() {
	
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Criteria crit = session.createCriteria(Problem.class);
			session.getTransaction().commit();
			return crit.list()!=null? (ArrayList<Problem>) crit.list() : new ArrayList<Problem>();
		}
		catch ( HibernateException e ) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		
		return null;
	}

}

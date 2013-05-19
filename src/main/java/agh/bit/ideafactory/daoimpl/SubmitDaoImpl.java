package agh.bit.ideafactory.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;

@Repository("submitDao")
public class SubmitDaoImpl implements SubmitDao{

    @Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void addSubmit(Submit submit) {
		sessionFactory.getCurrentSession().saveOrUpdate(submit);
		//sessionFactory.openSession().saveOrUpdate(submit); // Hibernate version without transaction
		//sessionFactory.getCurrentSession().close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Submit> getSubmitsByUser(User user) {
	
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("user_id", user.getId()));
		return crit.list()!= null ? (List<Submit>) crit.list() : new ArrayList<Submit>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Submit> getSubmitsByProblem(Problem problem) {
		
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Problem.class);
		crit.add(Restrictions.eq("problem_id", problem.getId()));
		return crit.list() != null ? (List<Submit>) crit.list() : new ArrayList<Submit>();
	
	}
	
	
}

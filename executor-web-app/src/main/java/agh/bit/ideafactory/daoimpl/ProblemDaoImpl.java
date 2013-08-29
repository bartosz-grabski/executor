package agh.bit.ideafactory.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

@Repository("problemDao")
public class ProblemDaoImpl extends BaseDaoImpl<Problem> implements ProblemDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getProblemsByUser(User user) {
		Session session = sessionFactory.getCurrentSession();

		Criteria crit = session.createCriteria(Problem.class);
		crit.add(Restrictions.eq("user", user));
		return crit.list() != null ? (ArrayList<Problem>) crit.list() : new ArrayList<Problem>();

	}

	@Override
	public Long getHighestProblemID() {
		Session session = sessionFactory.getCurrentSession();
		// DetachedCriteria maxId = DetachedCriteria.forClass(Problem.class)
		// .setProjection(Projections.max("id"));
		// Object highestProblemID = (session.createCriteria(Problem.class)
		// .add( Property.forName("id").eq(maxId) )
		// .list());
		// return (highestProblemID != null) ? (Long)(session.createCriteria(Problem.class)
		// .add( Property.forName("id").eq(maxId) )
		// .list().get(0)) : 0L;

		Criteria criteria = session.createCriteria(Problem.class, "problem");
		criteria.setProjection(Projections.max("problem.id"));
		return (Long) (criteria.list().get(0) != null ? criteria.list().get(0) : 0L);
	}

}
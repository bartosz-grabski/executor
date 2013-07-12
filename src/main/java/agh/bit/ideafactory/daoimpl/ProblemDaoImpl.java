package agh.bit.ideafactory.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
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
		return crit.list() != null ? (ArrayList<Problem>) crit.list()
				: new ArrayList<Problem>();

	}

}

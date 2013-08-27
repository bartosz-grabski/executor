package agh.bit.ideafactory.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;

@Repository("submitDao")
public class SubmitDaoImpl extends BaseDaoImpl<Submit> implements SubmitDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Submit> getSubmitsByUser(User user) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("user_id", user.getId()));
		return crit.list() != null ? (List<Submit>) crit.list() : new ArrayList<Submit>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Submit> getSubmitsByProblem(Problem problem) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("problem_id", problem.getId()));
		return crit.list() != null ? (List<Submit>) crit.list() : new ArrayList<Submit>();

	}

	@Override
	public Long getHighestIdOfUserSubmits(User user) {
		Criteria criteria = getCriteria();
		criteria.setProjection(Projections.max("s.id"));
		criteria.add(Restrictions.eq("s.user.id", user.getId()));
		return (Long) (criteria.uniqueResult() != null ? criteria.uniqueResult() : 0L);
	}

}

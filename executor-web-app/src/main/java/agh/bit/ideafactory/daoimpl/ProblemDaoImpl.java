package agh.bit.ideafactory.daoimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

@Repository("problemDao")
public class ProblemDaoImpl extends BaseDaoImpl<Problem> implements ProblemDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getProblemsByUser(User user) {
		Criteria crit = getCriteria();
		crit.add(Restrictions.eq("user", user));
		return crit.list() != null ? (ArrayList<Problem>) crit.list() : new ArrayList<Problem>();
	}

	@Override
	public Long getHighestProblemID() {
		Criteria criteria = getCriteria();
		criteria.setProjection(Projections.max("id"));
		return criteria.uniqueResult() != null ? (Long) criteria.uniqueResult() : 0L;
	}

	@Override
	public void saveProblem(Problem problem, MultipartFile problemFile) throws IOException {
		byte[] content = problemFile.getBytes();
		problem.setContent(content);
		sessionFactory.getCurrentSession().save(problem);
	}

}

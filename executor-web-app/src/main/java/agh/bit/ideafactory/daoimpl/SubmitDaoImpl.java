package agh.bit.ideafactory.daoimpl;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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

	@Override
	public Long getHighestIdOfUserSubmits(User user) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("user.id", user.getId()));
		criteria.setProjection(Projections.max("id"));
		return (Long) (criteria.uniqueResult() != null ? criteria.uniqueResult() : 0L);
	}

	@Override
	public Submit saveSubmit(Submit submit, MultipartFile file) throws HibernateException, IOException {
		Blob submitFileBlob = Hibernate.getLobCreator(sessionFactory.getCurrentSession()).createBlob(file.getBytes());

		submit.setSubmitFile(submitFileBlob);

		sessionFactory.getCurrentSession().save(submit);

		return submit;

	}

}

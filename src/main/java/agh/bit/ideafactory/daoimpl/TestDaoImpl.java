package agh.bit.ideafactory.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.model.Test;

@Repository("testDao")
public class TestDaoImpl extends BaseDaoImpl<Test> implements TestDao {

	public Long getHighestTestID() {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Test.class, "test");
		criteria.setProjection(Projections.max("test.id"));

		return (Long) (criteria.list().get(0) != null ? criteria.list().get(0) : 0L);
	}

}

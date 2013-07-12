package agh.bit.ideafactory.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.model.Result;

@Repository("resultDao")
public class ResultDaoImpl extends BaseDaoImpl<Result> implements ResultDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addResult(Result result) {
    	Session session = sessionFactory.getCurrentSession();
    	session.save(result);
	}

}

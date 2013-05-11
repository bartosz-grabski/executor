package agh.bit.ideafactory.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.model.Result;

@Repository("resultDao")
public class ResultDaoImpl implements ResultDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void addResult(Result result) {
		Session session = null;
    	try {
    		session = sessionFactory.openSession();
    		session.beginTransaction();
    		session.save(result);
    		session.getTransaction().commit();
    	}
    	catch ( HibernateException e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
    	}
    	finally {
    		session.close();
    	}
		
	}

}

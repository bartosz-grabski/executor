package agh.bit.ideafactory.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 19.04.13
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */


/*
 This is core class which fetches the data from the database.
 Note that the class contains a Resource names “sessionFactory”,
 whenever any method which contains a hibernate query is called,
 it needs hibernate session to execute the query and
 that session is provided by sessionFactory
 (which is a bean provided by spring to handle the hibernate session).
 */

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	

	@Autowired
    private SessionFactory sessionFactory;

//    @PersistenceContext
//    private EntityManager em;
//
//    private EntityManagerFactory emf;
//
//    @PersistenceUnit
//    public void setEntityManagerFactory(EntityManagerFactory emf) {
//        this.emf = emf;
//    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
   // @Transactional("transactionManager")
    public User getUserByUserName(String username) {
    	Session session = null;
    	try {
    		session = sessionFactory.openSession();
    		Criteria crit = session.createCriteria(User.class);
    		crit.add(Restrictions.eq("username", username));
    		return (User) crit.uniqueResult();
    	}
    	catch ( HibernateException e) {
    		e.printStackTrace();
    	}
    	finally {
    		session.close();
    	}
		return null;
    	
//        Query queryResult;
//        queryResult = getCurrentSession().createQuery("from User where username =:userName");
//        queryResult.setParameter("userName", username);
//        return (User) queryResult.list().get(0);
    }

	@Override
	public User getById(Long id) {
		
		Session session = null;
    	try {
    		session = sessionFactory.openSession();
    		Criteria crit = session.createCriteria(User.class);
    		crit.add(Restrictions.eq("id", id));
    		return (User) crit.uniqueResult();
    	}
    	catch ( HibernateException e) {
    		e.printStackTrace();
    	}
    	finally {
    		session.close();
    	}
		return null;
		
		
	}

	@Override
    public void addUser(User user) {
		Session session = null;
    	try {
    		session = sessionFactory.openSession();
    		session.beginTransaction();
    		session.save(user);
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

	@Override
	public void update(User user) {
		Session session = null;
    	try {
    		session = sessionFactory.openSession();
    		session.beginTransaction();
    		session.update(user);
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

	@Override
	public User getUserByUserNameFetched(String username) {
		
		Session session = null;
    	try {
    		session = sessionFactory.openSession();
    		Criteria crit = session.createCriteria(User.class);
    		crit.add(Restrictions.eq("username", username));
    		User user = (User) crit.uniqueResult();
    		user.getSubmits().size();
    		if ( user != null) {
    			Hibernate.initialize(user);
    			Hibernate.initialize(user.getSubmits());
    		}
    		return user;
    	}
    	catch ( HibernateException e) {
    		e.printStackTrace();
    	}
    	finally {
    		session.close();
    	}
		return null;
	}

   	
}

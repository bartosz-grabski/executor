package agh.bit.ideafactory.daoimpl;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.hibernate.criterion.Restrictions;

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

    @PersistenceContext
    private EntityManager em;

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional("transactionManager")
    public User getUserByUserName(String username) {
    	
    	//better version of Hibernate
    	Session session = sessionFactory.openSession();
    	Criteria crit = session.createCriteria(User.class);
    	crit.add(Restrictions.eq("username", username));
    	return (User) crit.uniqueResult();
    	
//        Query queryResult;
//        queryResult = getCurrentSession().createQuery("from User where username =:userName");
//        queryResult.setParameter("userName", username);
//        return (User) queryResult.list().get(0);
    }

	@Override
	public User getById(Long id) {
		
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("id", id));
		return (User) crit.uniqueResult();
	}

	@Override
    public void addUser(User u) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(u);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

   	
}

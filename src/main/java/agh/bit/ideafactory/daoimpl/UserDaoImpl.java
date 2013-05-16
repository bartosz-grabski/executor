package agh.bit.ideafactory.daoimpl;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.hibernate.Query;

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

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User getUserByUserName(String username) {
        Query queryResult;
        queryResult = getCurrentSession().createQuery("from User where username =:userName");
        queryResult.setParameter("userName", username);
        return (User) queryResult.list().get(0);
    }

    @Override
    public User getUserByUserNameFetched(String username) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getById(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addUser(User u) {
        Session session  = sessionFactory.getCurrentSession();
        session.save(u);
    }

}

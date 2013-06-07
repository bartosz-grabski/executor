package agh.bit.ideafactory.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
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

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User getUserByUserName(String username) {
        Query queryResult;
        queryResult = getCurrentSession().createQuery("from User where username =:userName");
        queryResult.setParameter("userName", username);
        return (User) queryResult.uniqueResult();
    }

    @Override
    public User getUserByUserNameFetched(String username) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username", username));
        User user = (User) crit.uniqueResult();

        if ( user != null) {
            Hibernate.initialize(user);
            Hibernate.initialize(user.getSubmits());
        }
        return user;
    }

    @Override
    public User getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("Id",id));
        User user = (User) crit.uniqueResult();
        Hibernate.initialize(user);
        return user;
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void addUser(User u) {
        Session session  = sessionFactory.getCurrentSession();
        session.save(u);
    }



}

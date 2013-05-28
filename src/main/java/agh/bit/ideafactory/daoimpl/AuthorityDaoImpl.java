package agh.bit.ideafactory.daoimpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.AuthorityDao;
import agh.bit.ideafactory.model.Authority;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 15.05.13
 * Time: 08:32
 * To change this template use File | Settings | File Templates.
 */
@Repository("authorityDao")
public class AuthorityDaoImpl implements AuthorityDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Authority findAuthority(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query queryResult;
        queryResult = session.createQuery("from Authority where authority =:authName");
        queryResult.setParameter("authName", name);
        return (Authority) queryResult.list().get(0);
    }
}

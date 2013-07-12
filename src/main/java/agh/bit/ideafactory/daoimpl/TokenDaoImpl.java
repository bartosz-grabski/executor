package agh.bit.ideafactory.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.TokenDao;
import agh.bit.ideafactory.model.Token;

/**
 * Created with IntelliJ IDEA. User: Bartek Date: 18.05.13 Time: 14:19 To change
 * this template use File | Settings | File Templates.
 */
@Repository
public class TokenDaoImpl extends BaseDaoImpl<Token> implements TokenDao {

	@Override
	public Token findTokenByUserId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Token.class);
		crit.add(Restrictions.eq("user_id", id));
		Token token = (Token) crit.uniqueResult();
		Hibernate.initialize(token);
		return token;
	}

	@Override
	public Token findToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Token.class);
		crit.add(Restrictions.eq("token", token));
		Token t = (Token) crit.uniqueResult();
		Hibernate.initialize(token);
		return t;
	}

}

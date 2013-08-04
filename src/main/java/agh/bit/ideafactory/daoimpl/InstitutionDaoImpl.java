package agh.bit.ideafactory.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.model.Institution;

/**
 * @author bgrabski
 */
@Repository("institutionDao")
public class InstitutionDaoImpl extends BaseDaoImpl<Institution> implements InstitutionDao {

	@Override
	public Institution getByEmail(String email) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("email", email));
		return (Institution) criteria.uniqueResult();
	}

}

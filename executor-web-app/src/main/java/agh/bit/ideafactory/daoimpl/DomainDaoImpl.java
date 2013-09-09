package agh.bit.ideafactory.daoimpl;

import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.model.Domain;

@Repository("domainDao")
public class DomainDaoImpl extends BaseDaoImpl<Domain> implements DomainDao {

}

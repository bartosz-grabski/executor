package agh.bit.ideafactory.serviceimpl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.IncorrectRegisterDataException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;

@Transactional
@Service("domainService")
public class DomainServiceImpl implements DomainService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private DomainDao domainDao;

	@Override
	public List<Domain> getDomainsByAdminName(String username) {
		User user = userDao.getUserByUserName(username);
		user.getDomainsAdmin().size();
		return user.getDomainsAdmin();
	}

	@Override
	public void createOrUpdate(Domain domain) {

		domainDao.saveOrUpdate(domain);
	}
}

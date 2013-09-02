package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;

@Transactional
@Service("domainService")
public class DomainServiceImpl implements DomainService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<Domain> getDomainsByAdminName(String username) {
		User user = userDao.getUserByUserName(username);
		user.getDomainsAdmin().size();
		return user.getDomainsAdmin();
	}
}

package agh.bit.ideafactory.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.IncorrectRegisterDataException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.ExecutorSaltSource;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;

@Transactional
@Service("domainService")
public class DomainServiceImpl implements DomainService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private DomainDao domainDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Domain> getDomainsByAdminName(String username) {
		User user = userDao.getUserByUserName(username);
		user.getDomainsAdmin().size();
		return user.getDomainsAdmin();
	}

	@Override
	public Domain create(Domain domain, Institution institution) throws NotUniquePropertyException {

		for (Domain existingDomain : institution.getDomains()) {
			if (existingDomain.getTitle().equalsIgnoreCase(domain.getTitle())) {
				throw new NotUniquePropertyException("Domain title must be unique among institution's domains!", Domain.class, "title");
			}
		}

		domain.setInstitution(institution);

		domain.setPassword(passwordEncoder.encodePassword(domain.getPassword(), ExecutorSaltSource.getSalt()));

		domainDao.save(domain);

		return domain;
	}

	@Override
	public boolean joinDomain(Long domainId, String password, String userName) throws PasswordMatchException {

		Domain domain = domainDao.findById(domainId);

		User user = userDao.getUserByUserName(userName);

		if (domain.getPassword().equals(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt()))) {
			domain.getUsers().add(user);
			user.getDomains().add(domain);

			domainDao.save(domain);
			userDao.save(user);

			return true;
		} else {
			throw new PasswordMatchException("Password entered doesnt match domain join password!");
		}
	}

	@Override
	public Domain findById(Long domainId) {
		return domainDao.findById(domainId);
	}

	@Override
	public Domain findByIdFetched(Long domainId) {

		Domain domain = domainDao.findById(domainId);
		if (domain != null) {
			domain.getAdmins().size();
			domain.getGroups().size();
		}

		return domain;
	}

	@Override
	public List<Domain> findAll() {
		return domainDao.findAll();
	}

	@Override
	public List<Domain> findAllNotJoinedYet(String username) {
		List<Domain> result = new ArrayList<>();
		User user = userDao.getUserByUserName(username);

		List<Domain> allDomains = domainDao.findAll();

		for (Domain domain : allDomains) {
			if (!user.getDomains().contains(domain)) {
				result.add(domain);
			}
		}

		return result;
	}
}
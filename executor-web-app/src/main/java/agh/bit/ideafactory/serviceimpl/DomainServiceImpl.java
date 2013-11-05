package agh.bit.ideafactory.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
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
import agh.bit.ideafactory.exception.NoObjectFoundException;
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

	@Override
	public List<User> getUsersWhoCanBecomeAdmins(Long domainId) {

		Domain domain = domainDao.findById(domainId);

		List<User> result = new ArrayList<>(domain.getUsers());
		Iterator<User> iterator = result.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (domain.getAdmins().contains(user)) {
				iterator.remove();
			}
		}

		return result;
	}

	@Override
	public Domain addAdminToDomain(Long domainId, Long userId) throws NoObjectFoundException {

		User user = userDao.findById(userId);

		Domain domain = domainDao.findById(domainId);

		if (domain == null) {
			throw new NoObjectFoundException(Domain.class);
		}
		if (user == null) {
			throw new NoObjectFoundException(User.class);
		}

		if (!domain.getAdmins().contains(user)) {
			domain.getAdmins().add(user);
			user.getDomainsAdmin().add(domain);
		}

		domainDao.saveOrUpdate(domain);

		return domain;

	}

	@Override
	public Domain deleteAdminFromDomain(Long domainId, Long userId) throws NoObjectFoundException {

		Domain domain = domainDao.findById(domainId);

		User user = userDao.findById(userId);

		if (domain == null) {
			throw new NoObjectFoundException(Domain.class);
		}
		if (user == null) {
			throw new NoObjectFoundException(User.class);
		}

		if (domain.getAdmins().contains(user)) {
			domain.getAdmins().remove(user);
			user.getDomainsAdmin().remove(domain);
			domainDao.saveOrUpdate(domain);
		}

		return domain;
	}

	@Override
	public boolean canCreateGroup(Long domainId, String userName) {

		User user = userDao.getUserByUserName(userName);

		Domain domain = domainDao.findById(domainId);

		if (domain.getAdmins().contains(user)) {
			return true;
		}

		return false;
	}

	/**
	 * Checks whether user is admin of domain 
	 * 
	 * @param 	domainId	the id of domain to be checked
	 * @param	username	the username of user to be checked 
	 * 
	 * 
	 * @return	false if user is not an admin or domain does not exists, true otherwise
	 */
	@Override
	public boolean isAdminOf(Long domainId, String username) {
		List<Domain> domains = getDomainsByAdminName(username);
		for (Domain d : domains) {
			if (d.getId().equals(domainId)) return true;
		}
		return false;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean deleteUserFromDomain(Long userId, Long domainId) throws NoObjectFoundException {
		Domain domain = domainDao.findById(domainId);
		User user = userDao.findById(userId);
		if (domain == null) {
			throw new NoObjectFoundException(Domain.class, "No domain with given id found!");
		}
		if (user == null) {
			throw new NoObjectFoundException(User.class, "No user with given id found!");
		}
		
		if (domain.getAdmins().contains(user)) { throw new UnsupportedOperationException("Lacks priviliges to delete an admin"); }
		
		user.getDomains().remove(domain);
		domain.getUsers().remove(user);
		
		return true;
	}
}

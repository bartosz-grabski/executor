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
import agh.bit.ideafactory.exception.NotUniquePropertyException;
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
		domainDao.save(domain);

		return domain;
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
}

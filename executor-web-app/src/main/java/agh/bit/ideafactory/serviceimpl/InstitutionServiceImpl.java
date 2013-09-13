package agh.bit.ideafactory.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.helpers.ExecutorSaltSource;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.service.InstitutionService;

/**
 * @author bgrabski
 */
@Service
public class InstitutionServiceImpl implements InstitutionService {

	@Autowired
	InstitutionDao institutionDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void addInstitution(Institution a) {

		String rawPassword = a.getPassword();
		String salt = a.getUsername();
		String encoded = passwordEncoder.encodePassword(rawPassword, ExecutorSaltSource.getSalt());
		a.setPassword(encoded);
		institutionDao.saveOrUpdate(a);
	}

	@Override
	@Transactional
	public Institution getInstitutionByEmail(String email) {
		return institutionDao.getByEmail(email);
	}
}

package agh.bit.ideafactory.authentication;

import agh.bit.ideafactory.model.Institution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import agh.bit.ideafactory.dao.InstitutionDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bgrabski
 */
@Service("institutionDetailsService")
public class InstitutionDetailsServiceImpl implements UserDetailsService {

	@Autowired
	InstitutionDao institutionDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String EXCEPTION_MESSAGE = "No such institution!";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
	 * Used for fetching institution from database
	 * 
	 * @param email
	 *            the email identifying the user whose data is required.
	 * @return a fully populated institution (never <code>null</code>)
	 * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
	 *             if the user could not be found or the user has no GrantedAuthority
	 */
	@Override
    @Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Institution institution = institutionDao.getByEmail(email);
        if (institution == null) {
            throw new UsernameNotFoundException(EXCEPTION_MESSAGE);
        } else {
            logger.info(institution.getUsername());
            logger.info(institution.getPassword() + " " + passwordEncoder.encodePassword("bartek",institution.getUsername()));
            return (UserDetails) institution;
        }
	}
}

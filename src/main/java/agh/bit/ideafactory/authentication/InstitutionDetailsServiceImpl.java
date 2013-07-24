package agh.bit.ideafactory.authentication;

import agh.bit.ideafactory.dao.InstitutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author bgrabski
 */
@Service("institutionDetailsService")
public class InstitutionDetailsServiceImpl implements UserDetailsService {

	@Autowired
    InstitutionDao institutionDao;
	/**
	 *  Used for fetching user from database
	 * 
	 * @param email the email identifying the user whose data is required.
	 * @return a fully populated institution (never <code>null</code>)
	 * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
	 *             if the user could not be found or the user has no GrantedAuthority
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return null;
	}
}

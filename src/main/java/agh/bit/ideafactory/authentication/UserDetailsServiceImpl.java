package agh.bit.ideafactory.authentication;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 19.04.13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        List<String> authorityList = new ArrayList<String>();
        String authority;
        try {
            user = userDao.getUserByUserName(username);
            if ( user == null) {
            	throw new UsernameNotFoundException("getUserByUserName returned null");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new UsernameNotFoundException("getUserByUserName returned null");
        }

        Set<Authority> authoritySet = user.getAuthoritySet();
        Iterator<Authority> authoritySetIterator = authoritySet.iterator();
        while(authoritySetIterator.hasNext()) {
            authority = authoritySetIterator.next().getAuthority();
            authorityList.add(authority);
        }
        user.setUserAuthorities(authorityList);
        return (UserDetails) user;
    }

}

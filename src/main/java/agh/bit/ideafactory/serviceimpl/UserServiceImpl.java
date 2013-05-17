package agh.bit.ideafactory.serviceimpl;

import agh.bit.ideafactory.dao.AuthorityDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.AuthorityService;
import agh.bit.ideafactory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 01.05.13
 * Time: 00:34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorityService authorityService;

    @Override
    @Transactional
    public void addUser(User user) {
        Authority userAuth = authorityService.findAuthority("ROLE_USER");
        user.setAuthoritySet(new HashSet<Authority>());
        user.getAuthoritySet().add(userAuth);
        userDao.addUser(user);
    }

	@Override
    @Transactional
	public User getById(Long id) {
		return userDao.getById(id);
	}

    @Override
    @Transactional
	public User getUserByUserName(String username) {
		return userDao.getUserByUserName(username);
	}

	@Override
    @Transactional
	public User getUserByUserNameFetched(String username) {
		return userDao.getUserByUserNameFetched(username);
	}
}

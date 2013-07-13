package agh.bit.ideafactory.serviceimpl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.AuthorityService;
import agh.bit.ideafactory.service.UserService;

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
        userDao.saveOrUpdate(user);
    }

	@Override
    @Transactional
	public User getById(Long id) {
		return userDao.findById(id);
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

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void update(User u) {
		userDao.update(u);
    }
}

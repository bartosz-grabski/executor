package agh.bit.ideafactory.serviceimpl;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    UserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

	@Override
	public User getById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public User getUserByUserName(String username) {
		return userDao.getUserByUserName(username);
	}

	@Override
	public User getUserByUserNameFetched(String username) {
		return userDao.getUserByUserNameFetched(username);
	}
}

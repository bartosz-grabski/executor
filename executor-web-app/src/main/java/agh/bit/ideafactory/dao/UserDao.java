package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.User;

public interface UserDao extends BaseDao<User> {

	public User getUserByUserName(String username);

	public User getUserByUserNameFetched(String username);

	public User getUserByEmail(String email);

}

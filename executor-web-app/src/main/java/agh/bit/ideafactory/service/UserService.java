package agh.bit.ideafactory.service;

import agh.bit.ideafactory.model.User;

/**
 * Created with IntelliJ IDEA. User: Bartek Date: 01.05.13 Time: 00:31 To change this template use File | Settings | File Templates.
 */
public interface UserService {
	void addUser(User user);

	public User getById(Long id);

	public User getUserByUserName(String username);

	public User getUserByEmail(String email);

	public void update(User u);

	User getUserByUserNameFetched(String username);

	User getUserWithDomains(String name);

}

package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 19.04.13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    public User getUserByUserName(String username);
    public void addUser(User u);
}

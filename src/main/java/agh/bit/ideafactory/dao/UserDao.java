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

    public void addUser(User u);
	
    public User getUserByUserName(String username);
    
    /**
     * Return User specified by id or null if not found
     * @param id
     * @return User with specified id or null if not found
     */
    public User getById(Long id);
    
    
    
}

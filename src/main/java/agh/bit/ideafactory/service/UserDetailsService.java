package agh.bit.ideafactory.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 19.04.13
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public interface UserDetailsService {
      public UserDetails loadUserByUserName(String username);

}

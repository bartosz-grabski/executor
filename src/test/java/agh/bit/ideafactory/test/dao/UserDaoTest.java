package agh.bit.ideafactory.test.dao;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.UserService;
import agh.bit.ideafactory.test.main.AbstractServiceTest;

public class UserDaoTest extends AbstractServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void getValidUserByUserName() {
		User user = userService.getUserByUserName("admin");
		Assert.assertNotNull(user);
	}
	
	@Test
	public void getNotValidUserByUserName() {
		User user = userService.getUserByUserName("takiego nie ma");
		Assert.assertNull(user);
	}
	
	@Test
	public void checkUsersAuthoritiesSet() {
		
		User user = userService.getUserByUserName("admin");
		Assert.assertNotNull(user);
		
		Authority authority = new Authority();
		authority.setId(1L);
		authority.setAuthority("ROLE_ADMIN");
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority);
		Assert.assertEquals(authorities, user.getAuthoritySet());
	}
	
	@Test
	public void addValidUser() {
		
		User user = new User();
		user.setEmail("user@mail.com");
		user.setUsername("createdUser");
		user.setPassword(passwordEncoder.encodePassword("user_password", "salt"));
		
		Set<Authority> authorities = new HashSet<Authority>();
		Authority authority = getAuthorityByName("ROLE_USER");
		
		Assert.assertNotNull(authority);
		authorities.add(authority);
		
		user.setAuthoritySet(authorities);
		
		userService.addUser(user);
		User userRetrieved = userService.getUserByUserName(user.getUsername());
		Assert.assertEquals("user@mail.com", userRetrieved.getEmail());
		Assert.assertEquals("createdUser", userRetrieved.getUsername());
		Assert.assertTrue(passwordEncoder.isPasswordValid(userRetrieved.getPassword(), "user_password", "salt"));
		Assert.assertEquals(authorities, userRetrieved.getAuthoritySet());
		
		
	}
	
}

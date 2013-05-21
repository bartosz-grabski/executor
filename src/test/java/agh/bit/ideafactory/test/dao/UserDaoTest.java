package agh.bit.ideafactory.test.dao;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

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
		assertNotNull(user);
	}
	
	@Test
	public void getNotValidUserByUserName() {
		User user = userService.getUserByUserName("takiego nie ma");
		assertNull(user);
	}
	
	@Test
	public void checkUsersAuthoritiesSet() {
		
		User user = userService.getUserByUserName("admin");
		assertNotNull(user);
		
		Authority authority = new Authority();
		authority.setId(1L);
		authority.setAuthority("ROLE_ADMIN");
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority);
		assertEquals(authorities, user.getAuthoritySet());
	}
	
	@Test
	public void addValidUser() {
		
		User user = new User();
		user.setEmail("user@mail.com");
		user.setUsername("createdUser");
		user.setPassword(passwordEncoder.encodePassword("user_password", "salt"));
		
		Set<Authority> authorities = new HashSet<Authority>();
		Authority authority = getAuthorityByName("ROLE_USER");
		
		assertNotNull(authority);
		authorities.add(authority);
		
		user.setAuthoritySet(authorities);
		
		userService.addUser(user);
		User userRetrieved = userService.getUserByUserName(user.getUsername());
		assertEquals("user@mail.com", userRetrieved.getEmail());
		assertEquals("createdUser", userRetrieved.getUsername());
		assertTrue(passwordEncoder.isPasswordValid(userRetrieved.getPassword(), "user_password", "salt"));
		assertEquals(authorities, userRetrieved.getAuthoritySet());
		
	}
	
	@Test
	public void getUserByIdValid() {
		
		Long id = 1L;
		User userreturned = userService.getById(id);
		
		assertNotNull(userreturned);
		assertEquals(id, userreturned.getId());
		
	}
	
	@Test
	public void getUserByIdNotValid() {
		
		Long id = ALL_USERS_COUNT+1;
		User userReturned = userService.getById(id);
		
		assertNull(userReturned);
	}
	
}

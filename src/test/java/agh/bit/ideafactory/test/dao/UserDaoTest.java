package agh.bit.ideafactory.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import agh.bit.ideafactory.test.main.AbstractDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;


public class UserDaoTest extends AbstractDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	@Transactional
	public void shouldReturnValidUserByUserName() {
		User user = userDao.getUserByUserName("admin");
		assertNotNull(user);
	}
	
	@Test
	@Transactional
	public void shouldReturnNullWhenGettingNotValidUserByUserName() {
		User user = userDao.getUserByUserName("No such user");
		assertNull(user);
	}
	
	@Test
	@Transactional
	public void shouldSaveAuthoritiesToNewUser() {
		
		User user = userDao.getUserByUserName("admin");
		assertNotNull(user);
		
		Authority authority = new Authority();
		authority.setId(1L);
		authority.setAuthority("ROLE_ADMIN");
		Set<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority);
		assertEquals(authorities, user.getAuthoritySet());
	}
	
	@Test
	@Transactional
	public void shouldAddValidUser() {
		
		User user = new User();
		user.setEmail("user@mail.com");
		user.setUsername("createdUser");
		user.setPassword(passwordEncoder.encodePassword("user_password", "salt"));
		
		Set<Authority> authorities = new HashSet<Authority>();
		Authority authority = getAuthorityByName("ROLE_USER");
		
		assertNotNull(authority);
		authorities.add(authority);
		
		user.setAuthoritySet(authorities);
		
		userDao.addUser(user);
		User userRetrieved = userDao.getUserByUserName(user.getUsername());
		assertEquals("user@mail.com", userRetrieved.getEmail());
		assertEquals("createdUser", userRetrieved.getUsername());
		assertTrue(passwordEncoder.isPasswordValid(userRetrieved.getPassword(), "user_password", "salt"));
		assertEquals(authorities, userRetrieved.getAuthoritySet());
		
	}
	
	@Test
	@Transactional
	public void shouldReturnValidUserById() {
		
		Long id = 1L;
		User userreturned = userDao.findById(id);
		
		assertNotNull(userreturned);
		assertEquals(id, userreturned.getId());
		
	}
	
	@Test
	@Transactional
	public void shouldReturnNullWhenGettingNotValidUserById() {
		
		Long id = ALL_USERS_COUNT+1;
		User userReturned = userDao.findById(id);
		
		assertNull(userReturned);
	}
	
}

package agh.bit.ideafactory.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.Assert;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.AuthorityService;
import agh.bit.ideafactory.serviceimpl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao userDao;
	
	@Mock
	private AuthorityService authorityService;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	
	@Test
	public void shouldDelegateToUserDao() {
		User user = mock(User.class);
		
		userServiceImpl.addUser(user);
		
		verify(userDao).addUser(user);	
	}
	
	@Test
	public void shouldAddProperAuthorityToUser() {
		
		User user = new User();
		Authority authority = mock(Authority.class);
		
		when(authorityService.findAuthority("ROLE_USER")).thenReturn(authority);
		
		userServiceImpl.addUser(user);
		HashSet<Authority> authoritiesExpected = (HashSet<Authority>) user.getAuthoritySet();
				
		verify(userDao).addUser(user);
		Assert.notEmpty(authoritiesExpected);
		Assert.isTrue(authoritiesExpected.contains(authority));
		
	}
	
	@Test
	public void shouldDelegateToDaoWhenGetById() {
		
		User user = mock(User.class);
		Long userId = 1L;
		when(userDao.getById(userId)).thenReturn(user);
		
		User resultUser = userServiceImpl.getById(userId);
		
		assertEquals(resultUser, user);
		
	}
	
	@Test
	public void shouldDelegateToGetUserByUsername() {
		
		User user = mock(User.class);
		String username = "username";
		when(userDao.getUserByUserName(username)).thenReturn(user);
		
		User resultUser = userServiceImpl.getUserByUserName(username);
		
		assertEquals(resultUser, user);
		
	}

	@Test
	public void shouldDelegateToGetUserByUsernameFetched() {
		
		User user = mock(User.class);
		String username = "username";
		when(userDao.getUserByUserNameFetched(username)).thenReturn(user);
		
		User resultUser = userServiceImpl.getUserByUserNameFetched(username);
		
		assertEquals(resultUser, user);
		
	}
	
}

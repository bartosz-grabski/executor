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

		verify(userDao).saveOrUpdate(user);
	}

	@Test
	public void shouldAddProperAuthorityToUser() {

		User user = new User();
		Authority authority = mock(Authority.class);

		when(authorityService.findAuthority("ROLE_USER")).thenReturn(authority);

		userServiceImpl.addUser(user);
		HashSet<Authority> authoritiesReceived = (HashSet<Authority>) user.getAuthoritySet();

		verify(userDao).saveOrUpdate(user);
		Assert.notEmpty(authoritiesReceived);
		Assert.isTrue(authoritiesReceived.contains(authority));

	}

	@Test
	public void shouldDelegateToDaoWhenGetById() {

		User expectedUser = mock(User.class);
		Long userId = 1L;
		when(userDao.findById(userId)).thenReturn(expectedUser);

		User resultUser = userServiceImpl.getById(userId);

		assertEquals(expectedUser, resultUser);

	}

	@Test
	public void shouldDelegateToGetUserByUsername() {

		User expectedUser = mock(User.class);
		String username = "username";
		when(userDao.getUserByUserName(username)).thenReturn(expectedUser);

		User resultUser = userServiceImpl.getUserByUserName(username);

		assertEquals(expectedUser, resultUser);

	}

	@Test
	public void shouldDelegateToGetUserByUsernameFetched() {

		User expectedUser = mock(User.class);
		String username = "username";
		when(userDao.getUserByUserNameFetched(username)).thenReturn(expectedUser);

		User resultUser = userServiceImpl.getUserByUserNameFetched(username);

		assertEquals(expectedUser, resultUser);

	}

	public void shouldDelegateToGetUserByEmail() {

		User expectedUser = mock(User.class);
		String email = "email";
		when(userDao.getUserByEmail(email)).thenReturn(expectedUser);

		User resultUser = userServiceImpl.getUserByEmail(email);

		assertEquals(expectedUser, resultUser);
	}

}

package agh.bit.ideafactory.test.service;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.authentication.UserDetailsServiceImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA. User: bgrabski Date: 27.05.13 Time: 16:06 To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserDetailsService userDetailsService = new UserDetailsServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(userDetailsService);
	}

	@Test
	public void shouldReturnValidUser() {
		User user = mock(User.class);
		String existingUser = "Gienek";
		when(userDao.getUserByUserName(existingUser)).thenReturn(user);

		userDetailsService.loadUserByUsername(existingUser);
	}

	@Test
	public void shouldThrowExceptionWhenNoUserFound() {

		String notExistingUser = "Andrzej";
		when(userDao.getUserByUserName(notExistingUser)).thenReturn(null);
		try {
			userDetailsService.loadUserByUsername(notExistingUser);
			Assert.fail("Should throw exception");
		} catch (UsernameNotFoundException e) {
		}
	}

}
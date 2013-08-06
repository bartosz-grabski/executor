package agh.bit.ideafactory.test.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import agh.bit.ideafactory.authentication.InstitutionDetailsServiceImpl;
import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.model.Institution;

/**
 * @author bgrabski
 */
public class InstitutionDetailsServiceTest {

	@Mock
	private InstitutionDao institutionDao;

	@InjectMocks
	private UserDetailsService institutionDetailsService = new InstitutionDetailsServiceImpl();

	private String existingMail = "some@mail.com";
	private String nonExistingMail = "some@other.com";
	private String email;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnValidInstitution() {
		Institution institution = mock(Institution.class);
		givenEmail(existingMail);
		when(institutionDao.getByEmail(email)).thenReturn(institution);
		assertEquals(institution, institutionDetailsService.loadUserByUsername(email));

	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldThrowExceptionWhenInstitutionNotFound() {
		givenEmail(nonExistingMail);
		when(institutionDao.getByEmail(email)).thenReturn(null);
		assertNull(institutionDetailsService.loadUserByUsername(email));
	}

	private void givenEmail(String email) {
		this.email = email;
	}

}

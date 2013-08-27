package agh.bit.ideafactory.test.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserDetailsService institutionDetailsService = new InstitutionDetailsServiceImpl();

	private String existingMail = "some@mail.com";
	private String nonExistingMail = "some@other.com";
	private String email;
	private final String ENCODED_PASS = "123456789";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnValidInstitution() {
		Institution institution = new Institution();
		givenEmail(existingMail);
		when(institutionDao.getByEmail(email)).thenReturn(institution);
		when(passwordEncoder.encodePassword(anyString(), anyString())).thenReturn(ENCODED_PASS);
		assertEquals(institution, institutionDetailsService.loadUserByUsername(email));

	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldThrowExceptionWhenInstitutionNotFound() {
		givenEmail(nonExistingMail);
		when(institutionDao.getByEmail(email)).thenReturn(null);
		institutionDetailsService.loadUserByUsername(email);
	}

	private void givenEmail(String email) {
		this.email = email;
	}

}

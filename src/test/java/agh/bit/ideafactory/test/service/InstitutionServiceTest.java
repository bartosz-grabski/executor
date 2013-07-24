package agh.bit.ideafactory.test.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.serviceimpl.InstitutionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.service.InstitutionService;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * @author bgrabski
 */
public class InstitutionServiceTest {

	private Institution institution;
	private String email;

	@InjectMocks
	private InstitutionService institutionService = new InstitutionServiceImpl();

	@Mock
	private InstitutionDao institutionDao;
    @Mock
    private PasswordEncoder passwordEncoder;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldDelegateAddingToDao() {
		givenInstitution();

        when(passwordEncoder.encodePassword(institution.getPassword(),institution.getUsername())).thenReturn("encoded");

		institutionService.addInstitution(institution);
		verify(institutionDao).saveOrUpdate(institution);
	}

	@Test
	public void shouldDelegateFetchingToDao() {
		givenEmail();
		institutionService.getInstitutionByEmail(email);
		verify(institutionDao).getByEmail(email);
	}

	private void givenInstitution() {
		institution = new Institution();
        institution.setPassword("Somepass");
        institution.setUsername("SomeMail@mail.pl");
        institution.setEnabled(false);
	}

	private void givenEmail() {
		email = "some@mail.pl";
	}
}

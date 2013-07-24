package agh.bit.ideafactory.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import agh.bit.ideafactory.model.Institution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.test.main.AbstractDaoTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bgrabski
 */
public class InstitutionDaoTest extends AbstractDaoTest {

	private String password;
    private String email;
    private boolean enabled;
    private static final String EXISTING_MAIL = "existing@institution.com";
    private static final String NON_EXISTING_MAIL = "nonexisting@institution.com";

    @Autowired
	InstitutionDao institutionDao;

	@Test
    @Transactional
	public void shouldAddInstitution() {

        givenEmail();
        givenIsEnabled();
        givenPassword();

        assertNull(institutionDao.getByEmail(email));

        Institution institution = new Institution();
        institution.setPassword(password);
        institution.setEmail(email);
        institution.setEnabled(enabled);

        institutionDao.save(institution);

        assertNotNull(institutionDao.getByEmail(email));
	}

	@Test
    @Transactional
	public void shouldFetchExistingInstitution() {

        Institution institution = institutionDao.getByEmail(EXISTING_MAIL);
        assertNotNull(institution);


	}

	@Test
    @Transactional
	public void shouldReturnNullWhenFetchingNonExistingInstitution() {
        Institution institution = institutionDao.getByEmail(NON_EXISTING_MAIL);
        assertNull(institution);
	}

    private void givenEmail() {
        this.email = "some@mail.com";
    }

    private void givenPassword() {
        this.password = "somepassword";
    }

    private void givenIsEnabled() {
        this.enabled = true;
    }

    private void givenIsDisabled() {
        this.enabled = false;
    }

}

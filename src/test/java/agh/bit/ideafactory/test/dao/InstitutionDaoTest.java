package agh.bit.ideafactory.test.dao;

import agh.bit.ideafactory.model.Institution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.test.main.AbstractDaoTest;

/**
 * @author bgrabski
 */
public class InstitutionDaoTest extends AbstractDaoTest {

	@Autowired
	InstitutionDao institutionDao;

	@Test
	public void shouldAddInstitution() {
        Institution institution = new Institution();

	}

	@Test
	public void shouldFetchExistingInstitution() {

	}

	@Test
	public void shouldReturnNullWhenFetchingNonExistingInstitution() {

	}

}

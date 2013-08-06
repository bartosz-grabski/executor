package agh.bit.ideafactory.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.test.main.AbstractDaoTest;
import static org.junit.Assert.*;

public class SubmitDaoTest extends AbstractDaoTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private SubmitDao submitDao;

	@Test
	@Transactional
	public void shouldGetHighestIdOfSubmitByUserWithSubmits() {

		User user = userDao.findById(1L);

		assertNotNull(user);
		assertEquals("admin", user.getUsername());

		Long submitId = submitDao.getHighestIdOfUserSubmits(user);
		assertEquals(3L, submitId, 0);
	}

	@Test
	@Transactional
	public void shouldGetZeroIdOfSubmitByUserWithoutSubmits() {

		User user = userDao.findById(3L);

		assertNotNull(user);
		assertEquals("domain", user.getUsername());

		Long submitId = submitDao.getHighestIdOfUserSubmits(user);
		assertEquals(0L, submitId, 0);
	}

}

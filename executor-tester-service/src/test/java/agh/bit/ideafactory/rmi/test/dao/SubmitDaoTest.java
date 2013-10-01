package agh.bit.ideafactory.rmi.test.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.dao.SubmitDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mvc-dispatcher-servlet.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(propagation = Propagation.NEVER)
public class SubmitDaoTest {
	
	@Autowired
	private SubmitDao submitDao;
	
	/**
	 * As defined in import sql there are 3 submits with ids 1-3
	 * 
	 */
	
	@Test
	@Transactional
	public void shouldReturnExistingSubmitObject() {
		Long id = 1L;
		Submit result = submitDao.getSubmitById(id);
		assertNotNull(result);
		assertEquals(result.getId(),  id);
	}
	
	@Test
	@Transactional
	public void shouldReturnNullOnQueryingForNonExistinObject() {
		Long id = 5L;
		Submit result = submitDao.getSubmitById(id);
		assertNull(result);
	}
	
}

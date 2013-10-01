package agh.bit.ideafactory.rmi.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.rmi.dao.ResultDao;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mvc-dispatcher-servlet.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(propagation = Propagation.NEVER)
public class ResultDaoTest {
	
	@Autowired
	private ResultDao resultDao;

	private Result result;
	
	@Test
	@Transactional
	public void shouldPersistResultObjectToDatabase() {
		givenResult();
		Long id = (Long) resultDao.save(this.result);
		Result result = resultDao.getResultById(id);
		assertNotNull(result);
		assertEquals(id, result.getId());
	}
	
	private void givenResult() {
		this.result = new Result();
		this.result.setStatus("C");
	}

}

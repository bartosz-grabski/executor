package agh.bit.ideafactory.test.service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.serviceimpl.ResultServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ResultServiceTest {

	@Mock
	ResultDao resultDao;

	@InjectMocks
	ResultServiceImpl resultService = new ResultServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSimplyDelegateAddingToDAO() {
		Result result = new Result();

		resultService.addResult(result);

		verify(resultDao).save(result);
	}
}

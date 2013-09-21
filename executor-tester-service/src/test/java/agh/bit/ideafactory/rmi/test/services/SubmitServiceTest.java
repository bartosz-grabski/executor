package agh.bit.ideafactory.rmi.test.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.dao.SubmitDao;
import agh.bit.ideafactory.rmi.services.SubmitService;

@RunWith(MockitoJUnitRunner.class)
public class SubmitServiceTest {
	
	@Mock
	private SubmitDao submitDao;
	
	@InjectMocks
	private SubmitService submitService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private Long id;
	
	@Test
	public void shouldDelegateToSubmitDaoWhenGetByIdInvoked() {
		Submit submit = new Submit();
		givenId(10L);
		when(submitDao.getSubmitById(id)).thenReturn(submit);
		assertEquals(submit, submitService.getSubmitById(id));
		verify(submitDao).getSubmitById(id);
	}
	
	private void givenId(Long id) {
		this.id = id;
	}

}

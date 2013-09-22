package agh.bit.ideafactory.rmi.host;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.services.SubmitService;
import agh.bit.ideafactory.rmi.services.TestService;
import agh.bit.ideafactory.rmi.utils.TesterDeliverer;

public class RMIHostServiceTest {
	
	@Mock
	private SubmitService submitService;
	
	@Mock
	private TestService testService;
	
	@Mock
	private TesterDeliverer deliverer;
	
	@InjectMocks
	private RMIHostService rmiService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private Long id;
	
	@Test
	public void shouldInvokeDeliverMethodWhenSuppliedWithProperId() {
		givenId(1L);
		Submit fetchedSubmit = new Submit();
		List<agh.bit.ideafactory.model.Test> fetchedTests = new ArrayList<>();
		
		fetchedSubmit.setId(id);
		
		
		when(submitService.getSubmitById(id)).thenReturn(fetchedSubmit);
		when(testService.getTestsBySubmit(fetchedSubmit)).thenReturn(fetchedTests);
		
		rmiService.testSubmit(id);
		verify(submitService).getSubmitById(id);
		verify(testService).getTestsBySubmit(fetchedSubmit);
		verify(deliverer).deliver(fetchedSubmit, fetchedTests);
		
	}
	
	private void givenId(Long id) {
		this.id = id;
	}

}

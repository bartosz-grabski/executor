package agh.bit.ideafactory.rmi.test.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.rmi.dao.ResultDao;
import agh.bit.ideafactory.rmi.model.TesterOutput;
import agh.bit.ideafactory.rmi.services.ResultService;

@RunWith(MockitoJUnitRunner.class)
public class ResultServiceTest {
	
	@Mock 
	private ResultDao resultDao;
	
	@InjectMocks
	private ResultService resultService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	private TesterOutput testerOutput;
	
	@Test
	public void shouldDelegateToResultDaoWhenPutResultWithTesterOutputInvoked() {
		List<Map<String,String>> testResults = new ArrayList<>();
		givenOutput("OK","1234", testResults);
		resultService.putResult(testerOutput);
		verify(resultDao).save(any(Result.class));
	}
	
	private void givenOutput(String resultCode, String submitId, List<Map<String,String>> testResults) {
		testerOutput = new TesterOutput();
		testerOutput.setResultCode(resultCode);
		testerOutput.setSubmitId(submitId);
		testerOutput.setTestResults(testResults);
	}

}

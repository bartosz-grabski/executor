package agh.bit.ideafactory.rmi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.model.TestResult;
import agh.bit.ideafactory.rmi.dao.ResultDao;
import agh.bit.ideafactory.rmi.model.TesterOutput;

@Service
public class ResultService {
	
	@Autowired
	private ResultDao resultDao;
	
	/**
	 * <pre>
	 * Fetches data from TesterOutput object thus creating a proper Result object,
	 * which is then persisted to the database </pre>
	 * @param output - TesterOutput object
	 */
	@Transactional
	public void putResult(TesterOutput output) {
		Result result = new Result();
		result.setStatus(output.getResultCode());
		List<TestResult> testResults = new ArrayList<>();
		for (Map<String,String> testResultMap : output.getTestResults()) {
			TestResult testResult = new TestResult();
			testResult.setId(Long.parseLong(testResultMap.get("id")));
			testResults.add(testResult);
		}
		result.setTestResults(testResults);
		resultDao.save(result);
	}
	

}

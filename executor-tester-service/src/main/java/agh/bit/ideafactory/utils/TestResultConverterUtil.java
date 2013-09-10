package agh.bit.ideafactory.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agh.bit.ideafactory.rmi.model.TestResult;

/**
 * Class for converting Map<String,String> to List<TestResult>. The former is an effect of JSON deserialization
 * @author bgrabski
 *
 */
public class TestResultConverterUtil {
	
	private static final int DEFAULT_SIZE = 10;
	
	/**
	 * Method responsible for conversion from Map<String,String> to List<TestResult>
	 * @param testResultsMap - map to be converted
	 * @return List of TestResults
	 */
	public static List<TestResult> convert(Map<String,String> testResultsMap) {
		List<TestResult> list = new ArrayList<TestResult>(DEFAULT_SIZE);
		for (String key : testResultsMap.keySet()) {
			TestResult testResult = new TestResult();
			testResult.setContent(testResultsMap.get(key));
			testResult.setName(key);
			list.add(testResult);
		}
		return list;
	}

}

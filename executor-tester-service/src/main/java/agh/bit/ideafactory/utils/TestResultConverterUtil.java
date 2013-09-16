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
	 * @param list2 - map to be converted
	 * @return List of TestResults
	 */
	public static List<TestResult> convert(List<Map<String, Object>> resultList) {
		List<TestResult> list = new ArrayList<TestResult>(DEFAULT_SIZE);
		for (Map<String,Object> map : resultList) {
			TestResult testResult = new TestResult();
			testResult.setId((Long)map.get("id"));
			testResult.setMemory((Integer) map.get("memory"));
			testResult.setTime((Integer) map.get("time"));
			testResult.setResultCode((String) map.get("result_code"));
			list.add(testResult);
		}
		return list;
	}

}

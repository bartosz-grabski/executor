package agh.bit.ideafactory.rmi.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Model class for handling transition between Java POJOs and JSON
 * @author bgrabski
 *
 */
public class TesterOutput {

	private String resultCode;

	private Map<String, String> testResults;

	@JsonProperty(value = "result_code")
	public String getResultCode() {
		return resultCode;
	}

	@JsonProperty(value = "result_code")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@JsonProperty(value = "test_results")
	public Map<String, String> getTestResult() {
		return testResults;
	}

	@JsonProperty(value = "test_results")
	public void setTestResults(Map<String, String> testResult) {
		this.testResults = testResult;
	}

}

package agh.bit.ideafactory.rmi.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Model class for handling transition between Java POJOs and JSON
 * @author bgrabski
 *
 */
public class TesterOutput {

	private String resultCode;

	private List<Map<String, Object>> testResults;
	
	private String submitId;

	@JsonProperty(value = "submit_id")
	public String getSubmitId() {
		return submitId;
	}

	@JsonProperty(value = "submit_id")
	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	@JsonProperty(value = "result_code")
	public String getResultCode() {
		return resultCode;
	}

	@JsonProperty(value = "result_code")
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@JsonProperty(value = "test_results")
	public List<Map<String, Object>> getTestResult() {
		return testResults;
	}

	@JsonProperty(value = "test_results")
	public void setTestResults(List<Map<String, Object>> testResult) {
		this.testResults = testResult;
	}

}

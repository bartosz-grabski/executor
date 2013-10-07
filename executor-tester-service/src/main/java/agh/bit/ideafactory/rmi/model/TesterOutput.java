package agh.bit.ideafactory.rmi.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Model class for tester output. JSON output is automatically bound by Jackson to a Java object.
 * @author bgrabski
 *
 */
public class TesterOutput {
	
	@JsonProperty("submit_id")
	private String submitId;
	@JsonProperty("result_code")
	private String resultCode;
	@JsonProperty("test_results")
	private List<Map<String,String>> testResults;
	
	public String getSubmitId() {
		return submitId;
	}
	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public List<Map<String, String>> getTestResults() {
		return testResults;
	}
	public void setTestResults(List<Map<String, String>> testResults) {
		this.testResults = testResults;
	}
	

}

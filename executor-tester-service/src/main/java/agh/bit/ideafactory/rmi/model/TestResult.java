package agh.bit.ideafactory.rmi.model;

/**
 * Test result model class result like {"id": 2, "result_code": "TLE", "memory": 2000, "time" : 20
 * 
 * @author bgrabski
 * 
 */
public class TestResult {

	private Long id;
	private String resultCode;
	private Integer memory;
	private Integer time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

}

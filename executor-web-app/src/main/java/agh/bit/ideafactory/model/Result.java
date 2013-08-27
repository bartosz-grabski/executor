package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Result")
public class Result implements Serializable {

	private static final long serialVersionUID = 5037202162306136351L;

	// public enum Status{
	// WAITING,OK, REJ,TLE,ANS,RTE,CME,NT,SYS; }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "result_id")
	private Long id;

	@Column(name = "score")
	private BigDecimal score;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "result_id")
	private List<TestResult> testResults;

	@Column(name = "status")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public List<TestResult> getTestResults() {
		return testResults;
	}

	public void setTestResults(List<TestResult> testResults) {
		this.testResults = testResults;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// public ResultStatus getStatus() {
	// return status;
	// }
	//
	// public void setStatus(ResultStatus status) {
	// this.status = status;
	// }

}

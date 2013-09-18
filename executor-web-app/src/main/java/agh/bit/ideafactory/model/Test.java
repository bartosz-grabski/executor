package agh.bit.ideafactory.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Test")
public class Test implements Serializable {

	private static final long serialVersionUID = -2209669052686837568L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_id")
	private Long id;

	@Lob
	@Column(name = "test_input_file")
	private byte[] testInputFile;

	@Lob
	@Column(name = "test_output_file")
	private byte[] testOutputFile;

	@ManyToOne
	private Problem problem;

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getTestInputFile() {
		return testInputFile;
	}

	public void setTestInputFile(byte[] testInputFile) {
		this.testInputFile = testInputFile;
	}

	public byte[] getTestOutputFile() {
		return testOutputFile;
	}

	public void setTestOutputFile(byte[] testOutputFile) {
		this.testOutputFile = testOutputFile;
	}

}

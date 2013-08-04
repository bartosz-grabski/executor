package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Test")
public class Test implements Serializable{

	private static final long serialVersionUID = -2209669052686837568L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;
	
	@Column(name = "input")
	private String input;
	
	@Column(name = "output")
	private String output;

    @ManyToOne
    private Problem problem;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	
}

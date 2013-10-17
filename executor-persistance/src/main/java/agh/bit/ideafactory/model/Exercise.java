package agh.bit.ideafactory.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Exercise")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exercise_id")
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Test> tests;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exercise")
	private List<Submit> submits;

	@ManyToOne
	@JoinColumn(name = "problem_id")
	private Problem problem;

	@Column(name = "deadline")
	private Date deadline;
	
	@Column(name = "active")
	private boolean active;
	
	public Exercise() {
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public List<Submit> getSubmits() {
		return submits;
	}

	public void setSubmits(List<Submit> submits) {
		this.submits = submits;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}

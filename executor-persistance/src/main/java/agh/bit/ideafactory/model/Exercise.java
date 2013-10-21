package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Exercise")
public class Exercise implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exercise_id")
	private Long id;

	@NotEmpty
	@NotNull
	@Column(name = "title")
	private String title;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Test> tests = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exercise")
	private List<Submit> submits = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "problem_id")
	private Problem problem;

	@Column(name = "deadline")
	private Date deadline;

	@Column(name = "active")
	private boolean active;

	@ManyToMany
	@JoinTable(name = "group_exercise", joinColumns = { @JoinColumn(name = "exercise_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	private List<Group> groups = new ArrayList<>();

	public Exercise() {
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	@JsonIgnore
	public List<Submit> getSubmits() {
		return submits;
	}

	public void setSubmits(List<Submit> submits) {
		this.submits = submits;
	}

	@JsonIgnore
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}

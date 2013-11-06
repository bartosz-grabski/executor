package agh.bit.ideafactory.model;

import agh.bit.ideafactory.model.helpers.LanguageEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Problem")
public class Problem implements Serializable {

	private static final long serialVersionUID = 1287075720776108307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "problem_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Lob
	@Column(name = "content")
	private byte[] content;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problem", fetch = FetchType.LAZY)
	private List<Test> tests = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problem")
	private List<Exercise> exercises = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
	private Set<LanguageEnum> solutionLanguages;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private boolean active;

	public Problem() {
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public Set<LanguageEnum> getSolutionLanguages() {
	return solutionLanguages;
	}

	public void setSolutionLanguages(Set<LanguageEnum> solutionLanguages) {
	this.solutionLanguages = solutionLanguages;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Problem [id=" + id + ", name=" + name + "]";
	}

}

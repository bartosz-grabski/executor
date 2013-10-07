package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.List;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problem", fetch = FetchType.EAGER)
	private List<Test> tests;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "problem")
	private List<Exercise> exercises;

	// private Set<LanguageEnum> languages;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

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

	// public Set<LanguageEnum> getLanguages() {
	// return languages;
	// }
	//
	// public void setLanguages(Set<LanguageEnum> languages) {
	// this.languages = languages;
	// }

	@Override
	public String toString() {
		return "Problem [id=" + id + ", name=" + name + "]";
	}

}
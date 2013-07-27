package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import agh.bit.ideafactory.helpers.LanguageEnum;

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

	@Column(name = "content")
	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "Problem [id=" + id + ", name=" + name + ", content=" + content + "]";
	}

}

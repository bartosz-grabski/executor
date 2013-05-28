package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.List;

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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="problem")
	private List<Submit> submits;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="problem_id")
	private List<Test> tests;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Submit> getSubmits() {
		return submits;
	}

	public void setSubmits(List<Submit> submits) {
		this.submits = submits;
	}

	
	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
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

	@Override
	public String toString() {
		return "Problem [id=" + id + ", name=" + name + ", content=" + content
				+   "]";
	}
	
	
}

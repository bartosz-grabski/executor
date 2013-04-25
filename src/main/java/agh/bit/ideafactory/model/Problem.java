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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="problem")
	private List<Submit> submits;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="problem")
	private List<Test> tests;
	

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
	
}

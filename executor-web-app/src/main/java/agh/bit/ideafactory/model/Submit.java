package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "submit")
public class Submit implements Serializable {

	private static final long serialVersionUID = -1648960374609245246L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "submit_id")
	private Long id;

	@Column(name = "commit_date")
	private Date commitDate;

	@Column(name = "file_path")
	private String filePath;

	@OneToOne(optional = true)
	@JoinColumn(name = "result_id", nullable = false, updatable = false, unique = true)
	private Result result;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "exercise_id")
	private Exercise exercise;

	public Date getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

}

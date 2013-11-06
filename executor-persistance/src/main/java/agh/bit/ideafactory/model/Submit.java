package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import agh.bit.ideafactory.model.helpers.LanguageEnum;

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

	@Column(name = "file_name")
	private String fileName;

	@Lob
	@Column(name = "content")
	private byte[] content;

	@OneToOne(optional = true)
	@JoinColumn(name = "result_id", nullable = false, updatable = false, unique = true)
	private Result result;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "exercise_id")
	private Exercise exercise;

	@Enumerated(EnumType.STRING)
	@Column(name = "language")
	private LanguageEnum languageEnum;

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

	public LanguageEnum getLanguageEnum() {
		return languageEnum;
	}

	public void setLanguageEnum(LanguageEnum languageEnum) {
		this.languageEnum = languageEnum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}

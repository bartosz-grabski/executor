package agh.bit.ideafactory.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Submit")
public class Submit implements Serializable{

	private static final long serialVersionUID = -1648960374609245246L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submit_id")
    private Long id;
	
	@Column(name = "commit_date")
	private Date commitDate;
	
	@OneToOne(optional = true)
	@JoinColumn(name="result_id", nullable=false, updatable=false)
	private Result result;
	
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
	
	
	
}

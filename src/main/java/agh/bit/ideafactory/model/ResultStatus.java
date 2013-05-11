package agh.bit.ideafactory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "ResultStatus")
public class ResultStatus {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_status_id")
	private int id; 
	
	private ResultStatusEnum resultStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ResultStatusEnum getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(ResultStatusEnum resultStatus) {
		this.resultStatus = resultStatus;
	}
	
}



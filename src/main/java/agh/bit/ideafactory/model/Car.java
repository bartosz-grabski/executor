package agh.bit.ideafactory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Car")
public class Car {

	@Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
	
    @Column(name="firstname", nullable=false)
    private String firstname;
 

    @Column(name="lastname", nullable=false)
    private String lastname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}

package agh.bit.ideafactory.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Long id;

	private String title;

	private String description;

	@ManyToMany(mappedBy = "groups")
	private List<User> users;

	@ManyToMany(mappedBy = "groupsAdmin")
	private List<User> admins;

	@ManyToOne
	@JoinColumn(name = "domain_id")
	private Domain domain;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getAdmins() {
		return admins;
	}

	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

}

package agh.bit.ideafactory.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Domain")
public class Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "domain_id")
	private Long id;

	private String title;

	private String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "domain")
	private List<Group> groups;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "domainsAdmin")
	private List<User> admins;

	@ManyToOne
	@JoinColumn(name = "institution_id")
	private Institution institution;

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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<User> getAdmins() {
		return admins;
	}

	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

}

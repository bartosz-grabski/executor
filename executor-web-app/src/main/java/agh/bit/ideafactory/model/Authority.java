package agh.bit.ideafactory.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA. User: Bartek Date: 19.04.13 Time: 13:08 To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "authorities")
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authority_id")
	private Long Id;

	@Column(name = "authority", unique = true)
	private String authority;

	public Long getId() {
		return Id;
	}

	public void setId(Long authorityId) {
		this.Id = authorityId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

}

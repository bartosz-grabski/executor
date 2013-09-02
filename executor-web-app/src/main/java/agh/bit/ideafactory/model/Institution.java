package agh.bit.ideafactory.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author bgrabski Class representing institution (business) account in Executor system
 */
@Entity
@Table(name = "Institution")
public class Institution implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "institution_id")
	private Long id;

	@Column(name = "email", unique = true)
	@Email
	@NotEmpty
	private String email;

	@Column(name = "password")
	@NotEmpty
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "institution", fetch = FetchType.EAGER)
	private List<Domain> domains;

	/**
	 * Institution model class has only one authority
	 * 
	 * @return always returns "ROLE_INSTITUTION" authority
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_INSTITUTION"));
	}

	/**
	 * Returns the password used to authenticate the user.
	 * 
	 * @return the password
	 */
	@Override
	public String getPassword() {
		return this.password; // To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Returns the username used to authenticate the user. Cannot return <code>null</code>.
	 * 
	 * @return the username (never <code>null</code>)
	 */
	@Override
	public String getUsername() {
		return this.email;
	}

	/**
	 * Indicates whether the user's account has expired. An expired account cannot be authenticated.
	 * 
	 * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
	 * 
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
	 * 
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
	 * 
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setUsername(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

}

package agh.bit.ideafactory.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="Users")
public class User implements UserDetails {

	private static final long serialVersionUID = -4398838559620935539L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long Id;
    
	
    @Column(name = "username", unique = true)
    @NotEmpty
    @Length(min = 6, max = 30)
    private String username;

    @Email
    @Column(name = "email", unique = true)
    @NotEmpty
    private String email;

    @NotEmpty
    @Length(min = 6)
    @Column(name = "password")
    private String password;
    
    @Column(name = "enabled")
    private Boolean enabled;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
    private List<Problem> problems;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private List<Submit> submits;
    
    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "user_authorities", joinColumns = {@JoinColumn(name="user_id")},
                inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<Authority> authoritySet;

    private transient Collection<GrantedAuthority> authorities;


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPassword() {
        return password;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUsername() {
        return username;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transient
    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transient
    public void setUserAuthorities(List<String> authorities) {
        List<GrantedAuthority> listOfAuthorities = new ArrayList<GrantedAuthority>();
        for (String role : authorities) {
            listOfAuthorities.add(new GrantedAuthorityImpl(role));
        }
        this.authorities = (Collection<GrantedAuthority>) listOfAuthorities;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long userId) {
        this.Id = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthoritySet() {
        return authoritySet;
    }

    public void setAuthoritySet(Set<Authority> authoritySet) {
        this.authoritySet = authoritySet;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}

	public List<Submit> getSubmits() {
		return submits;
	}

	public void setSubmits(List<Submit> submits) {
		this.submits = submits;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    @Override
    @Transient
    public boolean isEnabled() {
        return getEnabled();
    }

    @Override
    public String toString() {
        return this.username + this.getId();
    }
}

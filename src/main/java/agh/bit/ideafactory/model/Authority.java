package agh.bit.ideafactory.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 19.04.13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;
    
    @Column(name = "authority")
    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long authorityId) {
        this.id = authorityId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    
}

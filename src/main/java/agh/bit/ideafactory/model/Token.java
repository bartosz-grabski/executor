package agh.bit.ideafactory.model;

import javax.persistence.*;
import java.util.Date;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 17.05.13
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "token")
    private String token;
    @Column(name = "create_date")
    private Date createDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @PrePersist
    public void createTime() {
        this.setCreateDate(new Date());
    }

}

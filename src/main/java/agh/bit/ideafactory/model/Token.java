package agh.bit.ideafactory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @Column(name = "user_id")
    private int userId;
    @Column(name = "token")
    private String token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

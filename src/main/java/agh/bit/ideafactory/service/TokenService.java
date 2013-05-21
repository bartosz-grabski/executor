package agh.bit.ideafactory.service;

import org.springframework.stereotype.Service;

import agh.bit.ideafactory.model.Token;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 18.05.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */

public interface TokenService {
    public void saveToken(Token t);
    public Token findTokenById(int id);
    public Token findTokenByUserId(int id);
    public Token findToken(String token);
    public void updateToken(Token t);
    public void deleteToken(Token t);
}

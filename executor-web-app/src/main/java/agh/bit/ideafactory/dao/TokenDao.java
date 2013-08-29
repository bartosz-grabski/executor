package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.Token;

/**
 * Created with IntelliJ IDEA. User: Bartek Date: 18.05.13 Time: 13:59 To change this template use File | Settings | File Templates.
 */
public interface TokenDao extends BaseDao<Token> {

	public Token findTokenByUserId(int id);

	public Token findToken(String token);

}
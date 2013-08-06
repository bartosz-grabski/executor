package agh.bit.ideafactory.helpers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import agh.bit.ideafactory.model.Token;

/**
 * Created with IntelliJ IDEA. User: Bartek Date: 17.05.13 Time: 19:26 To change this template use File | Settings | File Templates.
 */
@Component
public class TokenGenerator {

	public Token generateToken() {
		String tokenContent = UUID.randomUUID().toString();
		return new Token(tokenContent);
	}

}

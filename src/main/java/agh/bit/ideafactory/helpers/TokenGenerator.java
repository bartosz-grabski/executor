package agh.bit.ideafactory.helpers;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 17.05.13
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TokenGenerator {

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}

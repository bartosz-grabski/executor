package agh.bit.ideafactory.exception;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 13.07.13
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */

/**
 * Thrown when no such parameter is present in HttpRequest
 */
public class NoSuchAttributeException extends Exception {

    public NoSuchAttributeException() {
        super();
    }
    public NoSuchAttributeException(String message) {
        super(message);
    }
}

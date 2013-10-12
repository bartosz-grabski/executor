package agh.bit.ideafactory.exception;

/**
 * Throw when service cannot find object in database
 * 
 * @author Grzesiek
 * 
 */
public class NoObjectFoundException extends Exception {

	private Class objectClass;

	public NoObjectFoundException(Class objectClass) {
		super();
		this.objectClass = objectClass;
	}

	public NoObjectFoundException(Class objectClass, String message) {
		super(message);
		this.objectClass = objectClass;
	}

}

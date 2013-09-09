package agh.bit.ideafactory.exception;

public class NotUniquePropertyException extends Exception {

	private Class<?> clazz;
	private String propertyName;

	public NotUniquePropertyException(String message, Class<?> clazz, String propertyName) {
		super(message);
		this.clazz = clazz;
		this.propertyName = propertyName;
	}

	public NotUniquePropertyException() {
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}

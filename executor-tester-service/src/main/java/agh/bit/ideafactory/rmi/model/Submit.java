package agh.bit.ideafactory.rmi.model;

public class Submit {
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return getPath();
	}

}

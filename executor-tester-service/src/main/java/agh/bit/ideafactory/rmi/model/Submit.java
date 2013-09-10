package agh.bit.ideafactory.rmi.model;

//TODO Submit, save exercise id
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

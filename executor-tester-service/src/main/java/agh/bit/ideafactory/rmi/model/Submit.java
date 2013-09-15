package agh.bit.ideafactory.rmi.model;

//TODO Submit, save exercise id
public class Submit {

	private long submitId;
	private String path;
	private byte[] content;
	private String language;
	
	public long getSubmitId() {
		return submitId;
	}
	
	public void setSubmitId(long submitId) {
		this.submitId = submitId;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return getPath();
	}

}

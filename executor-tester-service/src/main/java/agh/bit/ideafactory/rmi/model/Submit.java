package agh.bit.ideafactory.rmi.model;

//TODO Submit, save exercise id
public class Submit {

	private long submitId;
	private byte[] content;
	private String language;
	private Long exerciseId;
	
	public long getSubmitId() {
		return submitId;
	}

	public void setSubmitId(long submitId) {
		this.submitId = submitId;
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

	public Long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

}

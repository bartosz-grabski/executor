package agh.bit.ideafactory.form;

public class ExerciseForm {

	private String title;
	private String deadlineDate;
	private String deadlineTime;
	private boolean active;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}

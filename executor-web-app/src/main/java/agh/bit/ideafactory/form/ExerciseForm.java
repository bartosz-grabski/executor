package agh.bit.ideafactory.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import agh.bit.ideafactory.model.Exercise;

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

	public Exercise createExercise() {

		Exercise exercise = new Exercise();
		exercise.setTitle(title);
		exercise.setActive(active);
		if (deadlineDate != null && !deadlineDate.isEmpty()) {
			boolean deadlineTimeExists = deadlineTime != null && !deadlineTime.isEmpty();
			String dateString = deadlineTimeExists ? deadlineDate + " " + deadlineTime : deadlineDate;
			DateFormat dateFormat = new SimpleDateFormat(deadlineTimeExists ? "dd-MM-yyyy hh:mm" : "dd-MM-yyyy");
			try {
				Date date = dateFormat.parse(dateString);
				exercise.setDeadline(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return exercise;
	}
}

package agh.bit.ideafactory.rmi.test.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.services.TestService;

public class TestServiceTest {

	private TestService testService = new TestService();

	private Exercise exercise;
	private Submit submit;

	@Test
	public void shouldReturnValidListOfTestsWhenInvokedForExercise() {
		Long exerciseId = 10L;
		givenExercise(exerciseId);
		assertEquals(this.exercise.getTests(), testService.getTestsByExercise(this.exercise));
	}

	@Test
	public void shouldReturnValidListOfTestsWhenInvokedForSubmit() {
		Long exerciseId = 10L;
		Long submitId = 1L;
		givenSubmit(submitId, exerciseId);
		assertEquals(testService.getTestsBySubmit(this.submit), this.submit.getExercise().getTests());
	}

	private void givenExercise(Long id) {
		this.exercise = new Exercise();
		this.exercise.setId(id);
		this.exercise.setTests(new ArrayList<agh.bit.ideafactory.model.Test>());
	}

	private void givenSubmit(Long id, Long exerciseId) {
		this.submit = new Submit();
		this.submit.setId(id);

		Exercise exercise = new Exercise();
		exercise.setId(exerciseId);
		exercise.setTests(new ArrayList<agh.bit.ideafactory.model.Test>());
		this.submit.setExercise(exercise);
	}

}

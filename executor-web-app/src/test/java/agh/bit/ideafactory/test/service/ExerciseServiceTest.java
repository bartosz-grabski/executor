package agh.bit.ideafactory.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ExerciseService;
import agh.bit.ideafactory.serviceimpl.ExerciseServiceImpl;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {

	@Mock
	private ExerciseDao exerciseDao;

	@Mock
	private ProblemDao problemDao;

	@Mock
	private GroupDao groupDao;

	@InjectMocks
	private ExerciseService exerciseService = new ExerciseServiceImpl();

	@Test
	public void shouldDelegateToExerciseDao() {
		Long id = 10L;
		Exercise exercise = new Exercise();
		exerciseService.findAll();
		exerciseService.getById(id);
		exerciseService.update(exercise);

		verify(exerciseDao).findAll();
		verify(exerciseDao).findById(id);
		verify(exerciseDao).update(exercise);
	}

	@Test
	public void shouldGetExercisesThatCanBeAddedToGroup() {

		Group group = new Group();
		Long groupId = 1L;
		group.setId(groupId);
		Exercise exercise1 = new Exercise();
		exercise1.setId(1L);
		Exercise exercise2 = new Exercise();
		exercise1.setId(2L);

		List<Exercise> exercises = new ArrayList<>();
		exercises.add(exercise2);
		exercises.add(exercise1);

		Problem problem = new Problem();
		problem.setExercises(exercises);

		group.getExercises().add(exercise1);

		when(problemDao.findById(anyLong())).thenReturn(problem);
		when(groupDao.findById(groupId)).thenReturn(group);

		List<Exercise> result = exerciseService.getAllThatCanBeAddedToGroup(groupId, anyLong());

		assertEquals(1, result.size());
		assertEquals(exercise2, result.get(0));
	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenSavingExerciseAndNoProblemFound() throws NoObjectFoundException, NotUniquePropertyException {

		when(problemDao.findById(anyLong())).thenReturn(null);

		exerciseService.saveExercise(mock(Exercise.class), anyLong());

	}

	@Test(expected = NotUniquePropertyException.class)
	public void shouldThrowExceptionWhenSavingExerciseWithUnuniqueName() throws NotUniquePropertyException, NoObjectFoundException {

		Problem problem = new Problem();

		Exercise existingExercise = new Exercise();
		existingExercise.setTitle("existingExercise title");
		problem.getExercises().add(existingExercise);

		Exercise newExercise = new Exercise();
		newExercise.setTitle("existingExercise title");

		when(problemDao.findById(anyLong())).thenReturn(problem);

		exerciseService.saveExercise(newExercise, anyLong());

	}

	@Test
	public void shouldAddNewExercise() throws NotUniquePropertyException, NoObjectFoundException {

		Problem problem = new Problem();

		Exercise existingExercise = new Exercise();
		existingExercise.setTitle("existingExercise title");
		problem.getExercises().add(existingExercise);

		Exercise newExercise = new Exercise();
		newExercise.setTitle("newExercise title");

		when(problemDao.findById(anyLong())).thenReturn(problem);

		Exercise exercise = exerciseService.saveExercise(newExercise, anyLong());

		assertTrue(exercise.getProblem().equals(problem));

	}
}

package agh.bit.ideafactory.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.service.ExerciseService;
import agh.bit.ideafactory.serviceimpl.ExerciseServiceImpl;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {
	
	@Mock
	private ExerciseDao exerciseDao;
	
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

}

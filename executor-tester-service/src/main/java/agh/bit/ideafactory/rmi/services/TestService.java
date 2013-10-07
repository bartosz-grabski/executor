package agh.bit.ideafactory.rmi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;

@Service
public class TestService {
	
	public List<Test> getTestsBySubmit(Submit submit) {
		return getTestsByExercise(submit.getExercise());
	}
	
	public List<Test> getTestsByExercise(Exercise exercise) {
		return exercise.getTests();
	}

}

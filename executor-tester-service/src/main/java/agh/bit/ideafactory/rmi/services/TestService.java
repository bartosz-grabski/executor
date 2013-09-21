package agh.bit.ideafactory.rmi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.rmi.dao.ExerciseDao;

@Service
public class TestService {
	
	@Autowired
	private ExerciseDao exerciseDao;
	
	public List<Test> getTestsBySubmit(Submit submit) {
		return null;
	}
	
	public List<Test> getTestsByExercise(Exercise exercise) {
		return null;
	}

}

package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Exercise;

public interface ExerciseService {

	public List<Exercise> findAll();

	public Exercise getById(Long id);

	public void update(Exercise exercise);

	public Exercise saveExercise(Exercise exercise, Long problemId) throws NotUniquePropertyException, NoObjectFoundException;

	public List<Exercise> getAllByProblem(Long problemId);

	List<Exercise> getAllThatCanBeAddedToGroup(Long groupId, Long problemId);

}

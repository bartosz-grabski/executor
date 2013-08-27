package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.model.Exercise;

public interface ExerciseService {

	public List<Exercise> findAll();

	public Exercise getById(Long id);

}

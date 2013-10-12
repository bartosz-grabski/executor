package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;

public interface TestService {

	public List<Test> getTestsByProblem(Problem problem);

	public Test getTestByID(Long testID);
}

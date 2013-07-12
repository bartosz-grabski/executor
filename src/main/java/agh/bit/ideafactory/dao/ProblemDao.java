package agh.bit.ideafactory.dao;

import java.util.List;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.*;

public interface ProblemDao extends BaseDao<Problem>{

	public void addProblem(Problem problem);
	
	public List<Problem> getProblemsByUser(User user);
	
	public List<Problem> getProblems(); 
	
	public Problem getById(Long id);
}

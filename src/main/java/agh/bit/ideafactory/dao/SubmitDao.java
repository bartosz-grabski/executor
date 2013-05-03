package agh.bit.ideafactory.dao;

import java.util.List;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;

public interface SubmitDao {

	
	public void addSubmit(Submit submit);
	
	
	/**
	 * Get submits owned by specific User
	 * @param User
	 * @return list of submits, never null
	 */
	public List<Submit> getSubmitsByUser( User user);

	/**
	 * Get submits to specified problem
	 * @param problem
	 * @return list of submits, never null
	 */
	List<Submit> getSubmitsByProblem(Problem problem);
	
	
}

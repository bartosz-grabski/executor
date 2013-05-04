package agh.bit.ideafactory.service;

import java.util.List;


import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;


public interface SubmitService {

	public void addSubmit(Submit submit);
	
	public List<Submit> getSubmitsByUser( User user);
	
	List<Submit> getSubmitsByProblem(Problem problem);
	
}

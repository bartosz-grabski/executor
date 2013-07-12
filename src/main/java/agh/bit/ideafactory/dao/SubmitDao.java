package agh.bit.ideafactory.dao;

import java.util.List;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;

public interface SubmitDao extends BaseDao<Submit> {

	/**
	 * Get submits owned by specific User
	 * 
	 * @param User
	 * @return list of submits, never null
	 */
	public List<Submit> getSubmitsByUser(User user);

	/**
	 * Get submits to specified problem
	 * 
	 * @param problem
	 * @return list of submits, never null
	 */
	List<Submit> getSubmitsByProblem(Problem problem);

	/**
	 * 
	 * @param user
	 * @return max id from all submits by user, 0 if no submits
	 */
	public Long getHighestIdOfUserSubmits(User user);

}

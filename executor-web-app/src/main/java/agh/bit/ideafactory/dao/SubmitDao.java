package agh.bit.ideafactory.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.web.multipart.MultipartFile;

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
	 * 
	 * @param user
	 * @return max id from all submits by user, 0 if no submits
	 */
	public Long getHighestIdOfUserSubmits(User user);

	Submit saveSubmit(Submit submit, MultipartFile file) throws HibernateException, IOException;

}

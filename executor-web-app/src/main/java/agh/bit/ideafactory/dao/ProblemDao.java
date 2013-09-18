package agh.bit.ideafactory.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

public interface ProblemDao extends BaseDao<Problem> {

	public List<Problem> getProblemsByUser(User user);

	public Long getHighestProblemID();

	public void saveProblem(Problem problem, MultipartFile problemFile) throws IOException;

}

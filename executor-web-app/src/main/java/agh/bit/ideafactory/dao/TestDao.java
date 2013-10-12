package agh.bit.ideafactory.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;

public interface TestDao extends BaseDao<Test> {

	public Long getHighestTestID();

	public List<Test> getTestByProblem(Problem problem);

	public void saveTest(Test test, MultipartFile testInputFile, MultipartFile testOutputFile) throws IOException;
}

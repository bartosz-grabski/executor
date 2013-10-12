package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.service.TestService;

@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;

	@Override
	public List<Test> getTestsByProblem(Problem problem) {
		return testDao.getTestByProblem(problem);
	}

	@Override
	public Test getTestByID(Long testID) {
		return testDao.findById(testID);
	}
}

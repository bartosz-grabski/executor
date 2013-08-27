package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.helpers.TestType;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ProblemService;

@Service("problemService")
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private TestDao testDao;

	@Autowired
	private FileManager fileManager;

	@Override
	@Transactional
	public List<Problem> getProblems() {

		return problemDao.findAll();
	}

	@Override
	@Transactional
	public Problem getById(Long id) {
		return problemDao.findById(id);
	}

	@Override
	@Transactional
	public void addProblem(Problem problem) {
		problemDao.save(problem);
	}

	@Override
	@Transactional
	public void saveProblemOnServer(MultipartFile problemFile, List<MultipartFile> problemTestSet, User user, String title) throws IOException {

		List<Test> tests = new ArrayList<Test>();

		for (int i = 0; i < problemTestSet.size(); i++) {
			String inputTestFilePath = fileManager.saveTestFile(problemTestSet.get(i), TestType.INPUT, title);
			String outputTestFilePath = fileManager.saveTestFile(problemTestSet.get(++i), TestType.OUTPUT, title);
			Test test = prepareTest(inputTestFilePath, outputTestFilePath);
			testDao.save(test);
			tests.add(test);
		}

		String problemFilePath = fileManager.saveProblemFile(problemFile, title);

		Problem problem = prepareProblem(title, problemFilePath, tests, user);
		problemDao.save(problem);
	}

	private Problem prepareProblem(String title, String problemFilePath, List<Test> tests, User user) throws IOException {
		Problem problem = new Problem();
		problem.setUser(user);
		problem.setFilePath(problemFilePath);
		problem.setName(title);
		problem.setExercises(new ArrayList<Exercise>());
		problem.setTests(tests);

		return problem;
	}

	private Test prepareTest(String inputTestFilePath, String outputTestFilePath) {
		Test test = new Test();
		test.setInput(inputTestFilePath);
		test.setOutput(outputTestFilePath);
		return test;
	}
}

package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import agh.bit.ideafactory.helpers.FileManagerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.exception.FileExtensionException;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ProblemService;

@Service("problemService")
@Transactional
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private TestDao testDao;

	@Autowired
	private FileManager fileManager;

	@Override
	public List<Problem> getProblems() {
		return problemDao.findAll();
	}

	@Override
	public Problem getById(Long id) {
		return problemDao.findById(id);
	}

	@Override
	public void addProblem(Problem problem) {
		problemDao.save(problem);
	}

	@Override
	public void saveProblemOnServer(MultipartFile problemFile, List<MultipartFile> problemTestSet, User user, String title) throws IOException, FileExtensionException {

		Problem problem = prepareProblem(title, user);

		FileManagerUtils.checkFileExtension(problemFile, "txt");

		List<Test> tests = new ArrayList<>();
		Iterator testFileIterator = problemTestSet.iterator();
		while (testFileIterator.hasNext()) {
			MultipartFile inputTest = (MultipartFile) testFileIterator.next();
			MultipartFile outputTest = (MultipartFile) testFileIterator.next();

			FileManagerUtils.checkFileExtension(inputTest, "txt");
			FileManagerUtils.checkFileExtension(outputTest, "txt");
			Test test = prepareTest(problem, inputTest, outputTest);
			testDao.save(test);
			tests.add(test);
		}

		problem.setTests(tests);
        problem.setContent(problemFile.getBytes());
		problemDao.save(problem);
	}

	@Override
	public void addTestsToProblem(Problem problem, List<MultipartFile> problemTestSet) throws IOException, FileExtensionException {

		List<Test> tests = problem.getTests();
		Iterator testFileIterator = problemTestSet.iterator();
		while (testFileIterator.hasNext()) {
            MultipartFile testInput = (MultipartFile)testFileIterator.next();
            MultipartFile testOutput = (MultipartFile) testFileIterator.next();

            FileManagerUtils.checkFileExtension(testInput, "txt");
            FileManagerUtils.checkFileExtension(testOutput, "txt");
            Test test = prepareTest(problem, testInput, testOutput);

			testDao.save(test);
			tests.add(test);
		}
	}

	private Problem prepareProblem(String title, User user) throws IOException {
		Problem problem = new Problem();
		problem.setUser(user);
		problem.setName(title);
		problem.setExercises(new ArrayList<Exercise>());
		return problem;
	}

	private Test prepareTest(Problem problem, MultipartFile testInput, MultipartFile testOutput) throws IOException {
		Test test = new Test();
        test.setTestInputFile(testInput.getBytes());
        test.setTestOutputFile(testOutput.getBytes());
		test.setProblem(problem);
		return test;
	}
}

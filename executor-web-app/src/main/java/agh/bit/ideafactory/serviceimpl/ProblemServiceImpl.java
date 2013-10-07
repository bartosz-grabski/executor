package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private TestDao testDao;

	@Autowired
	private FileManager fileManager;

	public ProblemServiceImpl() {
	}

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
	public void saveProblemOnServer(MultipartFile problemFile, List<MultipartFile> problemTestSet, User user, String title) throws IOException, FileExtensionException {

		Problem problem = prepareProblem(title, user);

		checkFileExtension(problemFile, "txt");

		List<Test> tests = new ArrayList<>();
		Iterator testFileIterator = problemTestSet.iterator();
		while (testFileIterator.hasNext()) {
			MultipartFile inputTest = (MultipartFile) testFileIterator.next();
			MultipartFile outputTest = (MultipartFile) testFileIterator.next();

			checkFileExtension(inputTest, "txt");
			checkFileExtension(outputTest, "txt");
			Test test = prepareTest(problem);
			testDao.saveTest(test, inputTest, outputTest);
			tests.add(test);
		}

		problem.setTests(tests);
		problemDao.saveProblem(problem, problemFile);
	}

	private boolean checkFileExtension(MultipartFile file, String extension) throws FileExtensionException {
		String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
		if (fileExtension.equalsIgnoreCase(extension))
			return true;
		throw new FileExtensionException("Unsupported file extension. " + extension + " is required.");
	}

	@Override
	public void addTestsToProblem(Problem problem, List<MultipartFile> problemTestSet) throws IOException {

		List<Test> tests = problem.getTests();
		Iterator testFileIterator = problemTestSet.iterator();
		while (testFileIterator.hasNext()) {
			Test test = prepareTest(problem);
			testDao.saveTest(test, (MultipartFile) testFileIterator.next(), (MultipartFile) testFileIterator.next());
			tests.add(test);
		}
		problem.setTests(tests);
	}

	private Problem prepareProblem(String title, User user) throws IOException {
		Problem problem = new Problem();
		problem.setUser(user);
		problem.setName(title);
		problem.setExercises(new ArrayList<Exercise>());
		return problem;
	}

	private Test prepareTest(Problem problem) {
		Test test = new Test();
		test.setProblem(problem);
		return test;
	}
}

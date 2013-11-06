package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.util.*;

import agh.bit.ideafactory.form.ProblemForm;
import agh.bit.ideafactory.model.helpers.LanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.FileExtensionException;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.helpers.FileManagerUtils;
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
	private ExerciseDao exerciseDao;

	@Autowired
	private FileManager fileManager;

	@Autowired
	private UserDao userDao;

	@Override
	public List<Problem> getProblems() {
		return problemDao.findAll();
	}

	@Override
	public List<Problem> getProblems(boolean active) {
		return problemDao.findAll(active);
	}

	@Override
	public Problem getById(Long id) {
		Problem problem = problemDao.findById(id);
        problem.getSolutionLanguages().size();
        return problem;
	}

	@Override
	public void addProblem(Problem problem) {
		problemDao.save(problem);
	}

	@Override
	public void saveProblemOnServer(ProblemForm problemForm, User user) throws IOException, FileExtensionException {

        Problem problem = new Problem();

        problem.setName(problemForm.getProblemName());
        problem.setActive(problemForm.isActive());
        problem.setSolutionLanguages(problemForm.getLanguageSet());
        problem.setUser(user);

        MultipartFile problemFile = problemForm.getProblemFile();
        FileManagerUtils.checkFileExtension(problemFile, "txt");
        problem.setContent(problemFile.getBytes());

        List<Test> tests = new ArrayList<>();
        List<MultipartFile> testSet = problemForm.getTestSet();

		Iterator testFileIterator = testSet.iterator();
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
		problemDao.save(problem);
	}

	@Override
	public void addTestsToProblem(Long problemID, List<MultipartFile> problemTestSet) throws IOException, FileExtensionException {

		Problem problem = this.getById(problemID);
		List<Test> tests = problem.getTests();
		Iterator testFileIterator = problemTestSet.iterator();
		while (testFileIterator.hasNext()) {
			MultipartFile testInput = (MultipartFile) testFileIterator.next();
			MultipartFile testOutput = (MultipartFile) testFileIterator.next();

			FileManagerUtils.checkFileExtension(testInput, "txt");
			FileManagerUtils.checkFileExtension(testOutput, "txt");
			Test test = prepareTest(problem, testInput, testOutput);

			testDao.save(test);
			tests.add(test);
		}
	}

	private Test prepareTest(Problem problem, MultipartFile testInput, MultipartFile testOutput) throws IOException {
		Test test = new Test();
		test.setTestInputFile(testInput.getBytes());
		test.setTestOutputFile(testOutput.getBytes());
		test.setProblem(problem);
		return test;
	}

	@Override
	public void deleteProblem(Problem problem) {
		problemDao.delete(problem);
	}

	@Override
	public void updateProblem(Problem problem) {
		problemDao.update(problem);

	}

	@Override
	@Transactional
	public void deleteProblem(Problem problem, boolean keepHistory) {

		if (keepHistory) {
			problem.setActive(false);
			problemDao.update(problem);
			for (Exercise exercise : problem.getExercises()) {
				exercise.setActive(false);
				exerciseDao.update(exercise);
			}
		} else {
			problemDao.delete(problem);
		}

	}

	@Override
	public List<Problem> findAllByUserName(String username) {

		User user = userDao.getUserByUserName(username);
		if (user == null) {
			return new ArrayList<>();
		}
		user.getProblems().size();
		return user.getProblems();
	}

}

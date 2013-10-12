package agh.bit.ideafactory.test.dao;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.test.main.AbstractDaoTest;

public class ProblemDaoTest extends AbstractDaoTest {

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private UserDao userDao;

	@Test
	@Transactional
	public void shouldReturnExistingProblemById() {
		Problem problem = problemDao.findById(1L);
		assertNotNull(problem);
		assertEquals(null, problem.getContent());
		assertEquals("ProblemName 1", problem.getName());
	}

	@Test
	@Transactional
	public void shouldReturnNullWhenGettingNotExistingProblemById() {

		Problem problem = problemDao.findById(ALL_PROBLEMS_COUNT + 1);
		assertNull(problem);
	}

	@Test
	@Transactional
	public void shouldReturnAllProblems() {
		Collection<Problem> problems = problemDao.findAll();
		assertEquals(ALL_PROBLEMS_COUNT, problems.size(), 0);
	}

	@Test
	@Transactional
	public void shouldReturnProblemListByUser() {

		User user = userDao.findById(1L);
		List<Problem> problems = problemDao.getProblemsByUser(user);

		assertEquals(3, problems.size(), 0);
		assertEquals(1L, problems.get(0).getId(), 0);
	}

	@Test
	@Transactional
	public void shouldAddValidProblem() {
		Problem problem = returnNewCompleteProblem();

		assertNull(problemDao.findById(ALL_PROBLEMS_COUNT + 1));
		problemDao.save(problem);
		Problem returnedProblem = problemDao.findById(problem.getId());
		assertEquals(problem, returnedProblem);
		assertEquals(problem.getId(), returnedProblem.getId());
		assertEquals(problem.getName(), returnedProblem.getName());
		assertArrayEquals(problem.getExercises().toArray(), returnedProblem.getExercises().toArray());

	}

}

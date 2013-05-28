package agh.bit.ideafactory.test.dao;

import java.util.ArrayList;
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

import static org.junit.Assert.*;

public class ProblemDaoTest  extends AbstractDaoTest {

	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	@Transactional
	public void shouldReturnExistingProblemById() {
	
		Problem problem = problemDao.getById(1L);
		assertNotNull(problem);
		assertEquals("ProblemContent 1", problem.getContent());
		assertEquals("ProblemName 1", problem.getName());	
	}
	
	@Test
	@Transactional
	public void shouldReturnNullWhenGettingNotExistingProblemById() {
	
		Problem problem = problemDao.getById(ALL_PROBLEMS_COUNT+1);
		assertNull(problem);
	}
	
	@Test
	@Transactional
	public void shouldReturnAllProblems() {
		Collection<Problem> problems = problemDao.getProblems();
		assertEquals(ALL_PROBLEMS_COUNT, problems.size(),0);
	}
	
	@Test
	@Transactional
	public void shouldReturnProblemListByUser() {
		
		User user = userDao.getById(1L);
		List<Problem> problems = problemDao.getProblemsByUser(user);
		
		assertEquals(1, problems.size(),0);
		assertEquals(1L, problems.get(0).getId(),0);
	}
	
	@Test
	@Transactional
	public void shouldAddValidProblem() {
		Problem problem = returnNewCompleteProblem();
		
		assertNull(problemDao.getById(ALL_PROBLEMS_COUNT+1));
		problemDao.addProblem(problem);
		Problem returnedProblem = problemDao.getById(problem.getId());
		assertEquals(problem.getContent(),returnedProblem.getContent());
		assertEquals(problem.getId(), returnedProblem.getId());
		assertEquals(problem.getName(), returnedProblem.getName());
		assertEquals(problem.getSubmits(), returnedProblem.getSubmits());
		assertEquals(problem.getTests(), returnedProblem.getTests());
		
		
		
	}
	
}

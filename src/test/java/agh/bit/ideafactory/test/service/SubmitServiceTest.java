package agh.bit.ideafactory.test.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.model.ResultStatusEnum;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.serviceimpl.SubmitServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SubmitServiceTest {

	@Mock
	private SubmitDao submitDao;
	
	@Mock
	private ResultDao resultDao;
	
	@Mock
	private FileManager fileManager;
	
	@Mock
	private ProblemDao problemDao;
	
	@InjectMocks
	private SubmitServiceImpl submitServiceImpl = new SubmitServiceImpl();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldDelegateGettingUserSubmitsToDao() {		
		@SuppressWarnings("unchecked")
		List<Submit> submitsList = mock(List.class);
		User user = mock(User.class);
		when(submitDao.getSubmitsByUser(user)).thenReturn(submitsList);
		
		List<Submit> resultList = submitServiceImpl.getSubmitsByUser(user);
		
		assertEquals(submitsList, resultList);
	}
	
	@Test
	public void shouldDelegateGettingProblemSubmitsToDao() {
		
		@SuppressWarnings("unchecked")
		List<Submit> submitList = mock(List.class);
		Problem problem = mock(Problem.class);
		when(submitDao.getSubmitsByProblem(problem)).thenReturn(submitList);
		
		List<Submit> resultList = submitDao.getSubmitsByProblem(problem);
		
		assertEquals(submitList, resultList);
		
	}
	
	@Test
	public void shouldCreateNewSubmit() throws IOException {
		submitServiceImpl.saveSubmitOnServer(null, null, null);
		
		verify(submitDao).addSubmit(any(Submit.class));
	}
	
	@Test
	public void shouldCreateNewResultForSubmit() throws IOException {		
		submitServiceImpl.saveSubmitOnServer(null, null, null);
		
		verify(resultDao).addResult(any(Result.class));
	}

	@Test
	public void shouldCreateSubmitFile() throws IOException {
		
		User user = mock(User.class);
		MultipartFile submittedFile = mock(MultipartFile.class);
		
		submitServiceImpl.saveSubmitOnServer(submittedFile, user, null);
		
		verify(fileManager).saveSubmitFile(submittedFile, user);
		
	}
	
	@Test
	public void shouldSetUserAndFilepath() {
		
		User user = mock(User.class);
		String submittedFile = "filepath";
		Long problemId = 3L;
		Problem problemReturnedByDao = mock(Problem.class);
		
		when(problemDao.getById(problemId)).thenReturn(problemReturnedByDao);
		
		Submit submit = submitServiceImpl.prepareSubmit(user, submittedFile, problemId );
		
		assertEquals(user, submit.getUser());
		assertEquals(submittedFile, submit.getFilePath());
		assertEquals(problemReturnedByDao, submit.getProblem());
		
	}
	
	@Test
	public void shouldPrepareNewResult() {
		
		Result expectedResult = mock(Result.class);
		when(expectedResult.getScore()).thenReturn(new BigDecimal(0));
		when(expectedResult.getStatus()).thenReturn(ResultStatusEnum.WAITING.toString());
		expectedResult.setStatus(ResultStatusEnum.WAITING.toString());
		
		Result preparedResult = submitServiceImpl.prepareResult();
		
		assertEquals(expectedResult.getScore(), preparedResult.getScore());
		assertEquals(expectedResult.getStatus(), preparedResult.getStatus());
		
	}
	
}

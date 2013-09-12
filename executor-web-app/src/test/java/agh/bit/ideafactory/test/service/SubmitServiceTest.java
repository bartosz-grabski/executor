package agh.bit.ideafactory.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.helpers.LanguageEnum;
import agh.bit.ideafactory.model.Exercise;
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

	@Mock
	private ExerciseDao exerciseDao;

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
	public void shouldCreateNewSubmit() throws IOException, SubmitLanguageException {

		User user = mock(User.class);
		Long exerciseId = 3L;
		MultipartFile submittedFile = mock(MultipartFile.class);
		Exercise exercise = mock(Exercise.class);

		when(exerciseDao.findById(anyLong())).thenReturn(exercise);
		submitServiceImpl.saveSubmitOnServer(submittedFile, user, exerciseId, "C");

		submitServiceImpl.prepareSubmit(user, exerciseId, LanguageEnum.C, null);

		verify(submitDao).saveSubmit(any(Submit.class), any(MultipartFile.class));
	}

	@Test
	public void shouldCreateNewResultForSubmit() throws IOException, SubmitLanguageException {

		User user = mock(User.class);
		Long exerciseId = 3L;
		MultipartFile submittedFile = mock(MultipartFile.class);

		submitServiceImpl.saveSubmitOnServer(submittedFile, user, exerciseId, "C");

		verify(resultDao).save(any(Result.class));
	}

	@Test
	public void shouldGetSubmitFileName() throws IOException, SubmitLanguageException {

		User user = mock(User.class);
		MultipartFile submittedFile = mock(MultipartFile.class);
		String languageName = "java";

		submitServiceImpl.saveSubmitOnServer(submittedFile, user, null, languageName);

		verify(fileManager).getSubmitFileName(user, LanguageEnum.JAVA);

	}

	@Test
	public void shouldSetSubmitUser() {

		User user = mock(User.class);
		String submittedFile = "filepath";
		Long exerciseId = 3L;

		Exercise exercise = mock(Exercise.class);
		LanguageEnum languageEnum = LanguageEnum.C;
		when(exerciseDao.findById(exerciseId)).thenReturn(exercise);

		Submit submit = submitServiceImpl.prepareSubmit(user, exerciseId, languageEnum, submittedFile);

		assertEquals(user, submit.getUser());
		assertEquals(submittedFile, submit.getFileName());
		assertEquals(languageEnum, submit.getLanguageEnum());
		assertEquals(exercise, submit.getExercise());

	}

	@Test
	public void shouldPrepareNewResult() {

		Result expectedResult = mock(Result.class);
		when(expectedResult.getScore()).thenReturn(new BigDecimal(0));
		when(expectedResult.getStatus()).thenReturn(ResultStatusEnum.WAITING.toString());

		Result preparedResult = submitServiceImpl.prepareResult();

		assertEquals(expectedResult.getScore(), preparedResult.getScore());
		assertEquals(expectedResult.getStatus(), preparedResult.getStatus());

	}

	@Test(expected = SubmitLanguageException.class)
	public void shouldThrowExceptionOnNoMatchingFileExtension() throws IOException, SubmitLanguageException {

		User user = mock(User.class);
		MultipartFile submittedFile = mock(MultipartFile.class);

		String languageName = "not existing language name";

		submitServiceImpl.saveSubmitOnServer(submittedFile, user, null, languageName);

	}

	@Test
	public void shouldFindExistingLanguageFromLanguageName() throws IOException, SubmitLanguageException {
		User user = mock(User.class);
		String languageName = "java";
		Long exerciseId = 3L;

		Exercise exercise = mock(Exercise.class);
		LanguageEnum languageEnum = LanguageEnum.JAVA;
		MultipartFile multipartFile = mock(MultipartFile.class);

		when(exerciseDao.findById(exerciseId)).thenReturn(exercise);

		Submit preparedSubmit = submitServiceImpl.prepareSubmit(user, exerciseId, languageEnum, languageName);

		when(submitDao.saveSubmit(any(Submit.class), any(MultipartFile.class))).thenReturn(preparedSubmit);
		Submit submit = submitServiceImpl.saveSubmitOnServer(multipartFile, user, exerciseId, languageName);

		assertEquals(user, submit.getUser());
		assertEquals(languageName, submit.getFileName());
		assertEquals(languageEnum, submit.getLanguageEnum());
		assertEquals(exercise, submit.getExercise());
	}

	@Test
	public void shouldRetrieveLanguageFromFileExtension() throws HibernateException, IOException, SubmitLanguageException {
		User user = mock(User.class);
		String fileName = "plik.java";
		Long exerciseId = 3L;

		Exercise exercise = mock(Exercise.class);
		LanguageEnum languageEnum = LanguageEnum.JAVA;
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getOriginalFilename()).thenReturn(fileName);

		when(exerciseDao.findById(exerciseId)).thenReturn(exercise);
		Submit preparedSubmit = submitServiceImpl.prepareSubmit(user, exerciseId, languageEnum, null);

		when(submitDao.saveSubmit(any(Submit.class), any(MultipartFile.class))).thenReturn(preparedSubmit);
		Submit submit = submitServiceImpl.saveSubmitOnServer(multipartFile, user, exerciseId, null);

		assertEquals(user, submit.getUser());
		assertEquals(languageEnum, submit.getLanguageEnum());
		assertEquals(exercise, submit.getExercise());
	}

}

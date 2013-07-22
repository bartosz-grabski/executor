package agh.bit.ideafactory.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import agh.bit.ideafactory.controller.ProblemController;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class ProblemControllerTest {

	@Mock
	private ProblemService problemService;

	@Mock
	private UserService userService;

	private ModelMap model;
	private HttpServletRequest request;

	@InjectMocks
	private ProblemController problemController = new ProblemController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		model = new ModelMap();
		request = new MockHttpServletRequest();
	}

	@Test
	public void shouldReturnProperViews() {

		assertEquals("problem/list", problemController.listProblems(model));
		assertEquals("problem/details",
				problemController.showProblem(model, anyLong()));

	}

	@Test
	public void shouldFindProblemsWhenListingAllProblems() {
		problemController.listProblems(model);
		verify(problemService).getProblems();
	}

	@Test
	public void shouldAddProblemsToModelWhenListingAllProblems() {

		List<Problem> problemsExpected = mock(ArrayList.class);

		when(problemService.getProblems()).thenReturn(problemsExpected);

		problemController.listProblems(model);

		assertTrue(model.containsAttribute("problemList"));
		assertEquals(problemsExpected, model.get("problemList"));
	}

	@Test
	public void shouldDelegateToServiceWhenShowingProblemDetails() {
		problemController.showProblem(model, anyLong());
		verify(problemService).getById(anyLong());
	}

	@Test
	public void shouldAddProblemToModelMapWhenShowingProblemDetails() {

		Problem expectedProblem = mock(Problem.class);

		when(problemService.getById(anyLong())).thenReturn(expectedProblem);

		problemController.showProblem(model, anyLong());

		assertTrue(model.containsAttribute("problem"));
		assertEquals(expectedProblem, model.get("problem"));

	}

	@Test
	public void shouldAddUserToModelWhenProblemFound() {

		Problem expectedProblem = mock(Problem.class);
		User expectedUser = mock(User.class);

		when(problemService.getById(anyLong())).thenReturn(expectedProblem);
		// when(expectedProblem.getUser()).thenReturn(expectedUser);
		when(userService.getUserByProblem(expectedProblem)).thenReturn(
				expectedUser);

		problemController.showProblem(model, anyLong());

		verify(problemService).getById(anyLong());
		assertTrue(model.containsAttribute("user"));
		assertEquals(expectedUser, model.get("user"));

	}

	@Test
	public void shouldNotAddUserToModelWhenNoProblemFound() {

		when(problemService.getById(anyLong())).thenReturn(null);

		problemController.showProblem(model, anyLong());

		assertTrue(model.containsAttribute("problem"));
		assertNull(model.get("problem"));

	}
}

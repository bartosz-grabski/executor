package agh.bit.ideafactory.test.controller;

import java.io.IOException;
import java.security.Principal;

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
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.controller.SubmitController;
import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.helpers.LanguageEnum;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.SubmitService;
import agh.bit.ideafactory.service.UserService;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SubmitControllerTest {

	@Mock
	private SubmitService submitService;

	@Mock
	private UserService userService;

	@InjectMocks
	private SubmitController controller = new SubmitController();

	private ModelMap model;
	private HttpServletRequest request;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		model = new ModelMap();
		request = new MockHttpServletRequest();
	}

	@Test
	public void shouldShowErrorWhenEmptyFile() {

		MultipartFile multipartFile = mock(MultipartFile.class);
		Principal principal = mock(Principal.class);

		when(multipartFile.isEmpty()).thenReturn(true);

		String returnView = controller.create(model, multipartFile, null, null, principal, request);

		assertTrue(model.containsAttribute("error"));
		assertTrue(model.containsAttribute("languages"));
		assertEquals("submit/send", returnView);
	}

	@Test
	public void shouldRedirectToProblemListWhenIOException() throws IOException, SubmitLanguageException {

		MultipartFile multipartFile = mock(MultipartFile.class);
		Principal principal = mock(Principal.class);
		String username = "username";
		String problemId = "1";
		User user = mock(User.class);

		when(multipartFile.isEmpty()).thenReturn(false);
		when(userService.getUserByUserName(username)).thenReturn(user);
		doThrow(new IOException()).when(submitService).saveSubmitOnServer(any(MultipartFile.class), any(User.class), any(Long.class), any(String.class));

		String returnView = controller.create(model, multipartFile, problemId, null, principal, request);

		assertEquals("redirect:/problem/list", returnView);
	}

	@Test
	public void shouldShowErrorWhenSubmitLanguageExceptionThrown() throws IOException, SubmitLanguageException {

		MultipartFile multipartFile = mock(MultipartFile.class);
		Principal principal = mock(Principal.class);
		String username = "username";
		String problemId = "1";
		User user = mock(User.class);

		when(multipartFile.isEmpty()).thenReturn(false);
		when(userService.getUserByUserName(username)).thenReturn(user);
		doThrow(new SubmitLanguageException("")).when(submitService).saveSubmitOnServer(any(MultipartFile.class), any(User.class), any(Long.class), any(String.class));

		String returnView = controller.create(model, multipartFile, problemId, null, principal, request);

		assertTrue(model.containsAttribute("error"));
		assertTrue(model.containsAttribute("languages"));
		assertEquals("submit/send", returnView);
	}

	@Test
	public void shouldRedirectToSubmitListWhenNoErrors() throws IOException, SubmitLanguageException {

		MultipartFile multipartFile = mock(MultipartFile.class);
		Principal principal = mock(Principal.class);
		String username = "username";
		String problemId = "1";
		User user = mock(User.class);

		when(multipartFile.isEmpty()).thenReturn(false);
		when(userService.getUserByUserName(username)).thenReturn(user);

		String returnView = controller.create(model, multipartFile, problemId, null, principal, request);

		assertEquals("redirect:/submit/list", returnView);
	}

}

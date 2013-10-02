package agh.bit.ideafactory.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import agh.bit.ideafactory.controller.GroupController;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.GroupService;
import agh.bit.ideafactory.service.UserService;
import agh.bit.ideafactory.test.helpers.SecurityContextHelper;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

	@Mock
	private GroupService groupService;

	@Mock
	private DomainService domainService;

	@Mock
	private BeanValidator beanValidator;

	@Mock
	private UserService userService;

	@InjectMocks
	private GroupController controller;

	private ModelMap map;
	private BindingResult bindingResult;

	@Before
	public void setUp() {
		bindingResult = mock(BindingResult.class);
		map = new ModelMap();
	}

	@Test
	public void shouldSetErrorWhenValidationFails() {

		Domain domain = new Domain();
		Group group = new Group();
		when(domainService.findByIdFetched(anyLong())).thenReturn(domain);
		when(bindingResult.hasErrors()).thenReturn(true);

		controller.createGroup(group, 1L, map, bindingResult);

		assertTrue(map.containsAttribute("domain"));
		assertEquals(true, map.get("error"));
	}

	@Test
	public void shouldSetErrorOnExceptionCaught() throws NotUniquePropertyException {
		Domain domain = new Domain();
		Group group = new Group();

		when(domainService.findByIdFetched(anyLong())).thenReturn(domain);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(groupService.save(group, domain)).thenThrow(NotUniquePropertyException.class);

		controller.createGroup(group, 1L, map, bindingResult);

		assertTrue(map.containsAttribute("domain"));
		assertEquals(true, map.get("error"));
		assertFalse(map.containsAttribute("success"));
	}

	@Test
	public void shouldSetSuccessMessageWhenProperGroupCreation() {
		Domain domain = new Domain();
		Group group = new Group();

		when(domainService.findByIdFetched(anyLong())).thenReturn(domain);
		when(bindingResult.hasErrors()).thenReturn(false);

		controller.createGroup(group, 1L, map, bindingResult);

		assertTrue(map.containsAttribute("domain"));
		assertFalse(map.containsAttribute("error"));
		assertTrue(map.containsAttribute("success"));
	}

	@Test
	public void shouldUnableUserToJoinGroup() {

		SecurityContextHelper.initSpringSecurityContext("admin");

		Group group = new Group();
		User user = new User();
		List<Group> groups = new ArrayList<>();
		groups.add(group);
		user.setGroups(groups);
		ModelMap modelMap = new ModelMap();

		when(groupService.findById(anyLong())).thenReturn(group);
		when(userService.getUserByUserNameFetched(anyString())).thenReturn(user);

		controller.joinGroupForm(anyLong(), modelMap);

		assertTrue(modelMap.containsAttribute("canJoin"));
		assertEquals(false, modelMap.get("canJoin"));
	}

	@Test
	public void shouldEnableUserToJoinGroup() {

		SecurityContextHelper.initSpringSecurityContext("admin");

		Group group = new Group();
		User user = new User();
		List<Group> groups = new ArrayList<>();
		user.setGroups(groups);
		ModelMap modelMap = new ModelMap();

		when(groupService.findById(anyLong())).thenReturn(group);
		when(userService.getUserByUserNameFetched(anyString())).thenReturn(user);

		controller.joinGroupForm(anyLong(), modelMap);

		assertTrue(modelMap.containsAttribute("canJoin"));
		assertEquals(true, modelMap.get("canJoin"));
	}

	@Test
	public void shouldSetErrorWhenPasswordDoesntMatchDuringGroupJoining() throws PasswordMatchException {
		String userName = "admin";
		SecurityContextHelper.initSpringSecurityContext(userName);

		ModelMap map = new ModelMap();

		when(groupService.joinGroup(null, userName, null)).thenThrow(PasswordMatchException.class);

		controller.joinGroup(null, null, map);

		assertEquals(true, map.get("canJoin"));
		assertEquals(true, map.get("error"));
	}

	@Test
	public void shouldSetSuccessWhenUserJoinedGroup() throws PasswordMatchException {
		String userName = "admin";
		SecurityContextHelper.initSpringSecurityContext(userName);

		ModelMap map = new ModelMap();
		when(groupService.joinGroup(null, userName, null)).thenReturn(new Group());

		controller.joinGroup(null, null, map);

		assertEquals(true, map.get("success"));
	}

}

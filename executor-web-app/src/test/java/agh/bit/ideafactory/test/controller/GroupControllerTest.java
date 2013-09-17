package agh.bit.ideafactory.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import agh.bit.ideafactory.controller.GroupController;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.GroupService;
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

}

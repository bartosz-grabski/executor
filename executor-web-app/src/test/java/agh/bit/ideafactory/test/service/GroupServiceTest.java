package agh.bit.ideafactory.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.service.GroupService;
import agh.bit.ideafactory.serviceimpl.GroupServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

	@Mock
	private DomainDao domainDao;

	@Mock
	private GroupDao groupDao;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private GroupService groupService = new GroupServiceImpl();

	@Test
	public void shouldDelegateToDaoWhenFindingGroup() {
		groupService.findById(anyLong());

		verify(groupDao).findById(anyLong());
	}

	@Test(expected = NotUniquePropertyException.class)
	public void shouldThrowExceptionWhenNotUniqueTitleWithinDomain() throws NotUniquePropertyException {

		String existingTitle = "Existing title";

		Domain domain = new Domain();
		Group existingGroup = new Group();
		existingGroup.setTitle(existingTitle);
		domain.getGroups().add(existingGroup);

		Group addedGroup = new Group();
		addedGroup.setTitle(existingTitle);

		groupService.save(addedGroup, domain);

	}

	@Test
	public void shouldEncodePasswordWhenSavingGroup() throws NotUniquePropertyException {

		String encodedPassword = "encodedPassword";
		Domain domain = new Domain();
		Group group = new Group();
		group.setPassword("oldPassword");

		when(passwordEncoder.encodePassword(anyString(), anyString())).thenReturn(encodedPassword);

		Group savedGroup = groupService.save(group, domain);

		assertEquals(encodedPassword, savedGroup.getPassword());

	}

	@Test
	public void shouldSaveProperGroup() throws NotUniquePropertyException {

		String title = "title";
		String desc = "desc";

		Domain domain = new Domain();
		domain.setId(100L);

		Group group = new Group();
		group.setTitle(title);
		group.setDescription(desc);

		Group savedGroup = groupService.save(group, domain);

		assertFalse(domain.getGroups().isEmpty());
		assertEquals(title, savedGroup.getTitle());
		assertEquals(desc, savedGroup.getDescription());
		assertEquals(domain.getId(), savedGroup.getDomain().getId());
	}
}

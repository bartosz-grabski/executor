package agh.bit.ideafactory.test.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.ExecutorSaltSource;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.User;
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

	@Mock
	private UserDao userDao;

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

	@Test
	public void shouldFindGroupWhenUserJoin() throws PasswordMatchException {

		groupService.joinGroup(anyLong(), null, null);

		verify(groupDao).findById(anyLong());
	}

	@Test
	public void shouldReturnFoundGroupWhenUserJoin() throws PasswordMatchException {

		Group group = new Group();
		User user = new User();
		String userName = "username";
		String password = "password";
		String passwordEncoded = "passwordEncoded";

		group.setPassword(passwordEncoded);

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(passwordEncoded);

		Group groupReturned = groupService.joinGroup(anyLong(), null, password);

		assertEquals(group, groupReturned);

		when(groupDao.findById(anyLong())).thenReturn(null);

		groupReturned = groupService.joinGroup(anyLong(), null, password);

		assertEquals(null, groupReturned);
	}

	@Test
	public void shouldFindUserWhenUserJoin() throws PasswordMatchException {

		Group group = new Group();
		User user = new User();
		String userName = "username";
		String password = "password";
		String passwordEncoded = "passwordEncoded";

		group.setPassword(passwordEncoded);

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(passwordEncoded);

		groupService.joinGroup(anyLong(), userName, password);

		verify(userDao).getUserByUserName(userName);

	}

	@Test
	public void shouldEncryptPasswordFromInputWhenUserJoin() throws PasswordMatchException {

		Group group = new Group();
		User user = new User();
		String userName = "username";
		String password = "password";
		String passwordEncoded = "passwordEncoded";

		group.setPassword(passwordEncoded);

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(passwordEncoded);

		groupService.joinGroup(anyLong(), userName, password);

		verify(userDao).getUserByUserName(userName);
		verify(passwordEncoder).encodePassword(anyString(), any());

	}

	@Test(expected = PasswordMatchException.class)
	public void shouldThrowExceptionIfPasswordDoesntMatchWhenUserJoin() throws PasswordMatchException {

		Group group = new Group();
		User user = new User();
		String userName = "username";
		String password = "password";
		String passwordEncoded = "passwordEncoded";
		String groupPasswordEncoded = "encodedGroupPassword";

		group.setPassword(groupPasswordEncoded);

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(passwordEncoded);

		groupService.joinGroup(anyLong(), userName, password);

	}

	@Test
	public void shouldAddUserToGroup() throws PasswordMatchException {

		Group group = new Group();
		User user = new User();
		String userName = "username";
		String password = "password";
		String passwordEncoded = "passwordEncoded";

		group.setPassword(passwordEncoded);

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(passwordEncoded);

		groupService.joinGroup(anyLong(), userName, password);

		assertTrue(user.getGroups().contains(group));
		assertTrue(group.getUsers().contains(user));
	}
}

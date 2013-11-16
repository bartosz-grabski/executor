package agh.bit.ideafactory.test.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.ExecutorSaltSource;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.Problem;
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

	@Mock
	private ExerciseDao exerciseDao;

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
	public void shouldFindGroupDuringGroupJoining() throws PasswordMatchException {

		groupService.joinGroup(anyLong(), null, null);

		verify(groupDao).findById(anyLong());
	}

	@Test
	public void shouldReturnFoundGroupDuringGroupJoining() throws PasswordMatchException {

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
	public void shouldFindUserDuringGroupJoining() throws PasswordMatchException {

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
	public void shouldEncryptPasswordFromInputDuringGroupJoining() throws PasswordMatchException {

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
	public void shouldThrowExceptionIfPasswordDoesntMatchDuringGroupJoining() throws PasswordMatchException {

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

	@Test
	public void shouldAddModeratorToGroupIfNotModeratorYet() throws NoObjectFoundException {
		User user = new User();
		user.setId(2L);
		Group group = new Group();
		group.setId(1L);

		when(userDao.findById(user.getId())).thenReturn(user);
		when(groupDao.findById(group.getId())).thenReturn(group);

		Group persistedGroup = groupService.addModerator(group.getId(), user.getId());

		assertTrue(user.getGroupsAdmin().contains(group));
		assertTrue(group.getAdmins().contains(user));

	}

	@Test
	public void shouldNotAddModeratorToGroupIfModeratorAlready() throws NoObjectFoundException {
		User user = new User();
		user.setId(2L);
		Group group = new Group();
		group.getAdmins().add(user);
		group.setId(1L);
		user.getGroupsAdmin().add(group);

		when(userDao.findById(user.getId())).thenReturn(user);
		when(groupDao.findById(group.getId())).thenReturn(group);

		Group persistedGroup = groupService.addModerator(group.getId(), user.getId());

		verify(groupDao).saveOrUpdate(group);

		assertTrue(user.getGroupsAdmin().contains(persistedGroup));
		assertTrue(persistedGroup.getAdmins().contains(user));
		assertEquals(1, user.getGroupsAdmin().size());
		assertEquals(1, persistedGroup.getAdmins().size());
	}

	@Test
	public void shouldGetUsersWhoCanBecomeModerators() {

		Group group = new Group();
		User user = new User();
		User user2 = new User();
		group.getUsers().add(user);
		group.getUsers().add(user2);
		group.getAdmins().add(user);
		group.setId(1L);

		user.setId(1L);
		user.getGroupsAdmin().add(group);

		user2.setId(2L);

		when(userDao.findById(user.getId())).thenReturn(user);
		when(groupDao.findById(group.getId())).thenReturn(group);

		List<User> usersWhoCanBeModerators = groupService.getUsersWhoCanBecomeModerators(group.getId());

		assertEquals(1, usersWhoCanBeModerators.size());
		assertTrue(usersWhoCanBeModerators.contains(user2));
	}

	@Test
	public void shouldDeleteModeratorFromDomain() throws NoObjectFoundException {

		Group group = new Group();
		User user = new User();
		User user2 = new User();
		group.getUsers().add(user);
		group.getUsers().add(user2);
		group.getAdmins().add(user);
		group.setId(1L);

		user.setId(1L);
		user.getGroupsAdmin().add(group);

		user2.setId(2L);

		when(userDao.findById(user.getId())).thenReturn(user);
		when(groupDao.findById(group.getId())).thenReturn(group);

		Group persistedGroup = groupService.deleteModeratorFromGroup(group.getId(), user.getId());

		verify(groupDao).saveOrUpdate(group);

		assertTrue(persistedGroup.getAdmins().isEmpty());
		assertTrue(persistedGroup.getUsers().contains(user));
		assertTrue(persistedGroup.getUsers().contains(user2));

	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenAddingExerciseAndNoGroupFound() throws NoObjectFoundException {

		when(groupDao.findById(anyLong())).thenReturn(null);

		groupService.addExercise(null, null);

	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenAddingExerciseAndNoExerciseFound() throws NoObjectFoundException {

		Group group = new Group();

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(exerciseDao.findById(anyLong())).thenReturn(null);

		groupService.addExercise(null, null);

	}

	@Test
	public void shouldAddExerciseToGroup() throws NoObjectFoundException {
		Group group = new Group();
		Exercise exercise = new Exercise();

		when(groupDao.findById(anyLong())).thenReturn(group);
		when(exerciseDao.findById(anyLong())).thenReturn(exercise);

		Exercise savedExercise = groupService.addExercise(null, null);

		assertTrue(savedExercise.getGroups().contains(group));
	}
}

package agh.bit.ideafactory.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.ExecutorSaltSource;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.serviceimpl.DomainServiceImpl;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DomainServiceTest {

	@Mock
	private UserDao userDao;

	@Mock
	private DomainDao domainDao;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private DomainService domainService = new DomainServiceImpl();

	private Domain domain;

	@Before
	public void setUp() {
		domain = new Domain();
		domain.setDescription("description");
		domain.setTitle("title");
	}

	@Test(expected = NotUniquePropertyException.class)
	public void shouldThrowExceptionWhenCreatingDomainWithNotUniqueTitle() throws NotUniquePropertyException {

		Institution institution = mock(Institution.class);
		List<Domain> institutionDomains = new ArrayList<>();
		Domain existingDomain = mock(Domain.class);
		when(existingDomain.getTitle()).thenReturn("Title");
		institutionDomains.add(existingDomain);

		when(institution.getDomains()).thenReturn(institutionDomains);

		domainService.create(domain, institution);

	}

	@Test
	public void shouldDelegateToDaoWhenCreatingDomain() throws NotUniquePropertyException {
		Institution institution = mock(Institution.class);
		Long institutionId = 100L;
		institution.setId(institutionId);
		List<Domain> institutionDomains = new ArrayList<>();
		Domain existingDomain = mock(Domain.class);
		when(existingDomain.getTitle()).thenReturn("Diferent title");
		institutionDomains.add(existingDomain);

		when(institution.getDomains()).thenReturn(institutionDomains);

		Domain persistedDomain = domainService.create(domain, institution);
		assertEquals(domain.getTitle(), persistedDomain.getTitle());
		assertEquals(domain.getDescription(), persistedDomain.getDescription());
		assertEquals(domain.getInstitution(), persistedDomain.getInstitution());
	}

	@Test
	public void shouldEncodePassword() throws NotUniquePropertyException {

		String password = "password";
		String encodedPassword = "encoded password";

		Domain domain = new Domain();
		domain.setPassword(password);

		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(encodedPassword);

		domainService.create(domain, mock(Institution.class));

		assertEquals(encodedPassword, domain.getPassword());

	}

	@Test
	public void shouldFindDomainWhenJoiningDomain() {

		String encodedPassword = "encodedPassword";
		Domain domain = new Domain();
		domain.setPassword(encodedPassword);

		when(domainDao.findById(anyLong())).thenReturn(domain);

		try {
			domainService.joinDomain(anyLong(), null, null);
		} catch (PasswordMatchException e) {
		}

		verify(domainDao).findById(anyLong());
	}

	@Test
	public void shouldReturnTrueWhenPasswordMatches() throws PasswordMatchException {

		String password = "password";
		String encodedPassword = "encodedPassword";

		User user = mock(User.class);
		Domain domain = new Domain();
		domain.setPassword(encodedPassword);

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(encodedPassword);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);

		boolean result = domainService.joinDomain(anyLong(), password, null);

		assertTrue(result);

	}

	@Test(expected = PasswordMatchException.class)
	public void shouldThrowExceptionWhenPaswordDoesntMatch() throws PasswordMatchException {
		String password = "password";
		String encodedPassword = "encodedPassword";
		String domainPassword = "domainPAss";

		User user = mock(User.class);
		Domain domain = new Domain();
		domain.setPassword(domainPassword);

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(encodedPassword);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);

		domainService.joinDomain(anyLong(), password, null);

	}

	@Test
	public void shouldAddUserToDomainList() throws PasswordMatchException {

		String password = "password";
		String encodedPassword = "encodedPassword";

		User user = new User();
		Domain domain = new Domain();
		domain.setPassword(encodedPassword);

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(encodedPassword);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);

		boolean result = domainService.joinDomain(anyLong(), password, null);

		assertTrue(domain.getUsers().contains(user));
		assertTrue(user.getDomains().contains(domain));
	}

	@Test
	public void shouldDelegateToDaosWhenPasswordMatches() throws PasswordMatchException {
		String password = "password";
		String encodedPassword = "encodedPassword";

		User user = new User();
		Domain domain = new Domain();
		domain.setPassword(encodedPassword);

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(passwordEncoder.encodePassword(password, ExecutorSaltSource.getSalt())).thenReturn(encodedPassword);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);

		boolean result = domainService.joinDomain(anyLong(), password, null);

		verify(domainDao).save(domain);
		verify(userDao).save(user);

	}

	@Test
	public void shouldFindUserWhenFindingAllNotJoinedDomains() {

		User user = mock(User.class);
		when(userDao.getUserByUserName(anyString())).thenReturn(user);

		domainService.findAllNotJoinedYet(anyString());

		verify(userDao).getUserByUserName(anyString());
	}

	@Test
	public void shouldNotReturnDomainsAlreadyJoined() {

		User user = new User();

		Domain domainJoined = new Domain();
		domainJoined.setId(1L);
		domainJoined.getUsers().add(user);

		Domain domainNotJoined = new Domain();
		domainNotJoined.setId(2L);

		user.getDomains().add(domainJoined);

		List<Domain> allDomains = new ArrayList<>();
		allDomains.add(domainNotJoined);
		allDomains.add(domainJoined);

		when(userDao.getUserByUserName(anyString())).thenReturn(user);
		when(domainDao.findAll()).thenReturn(allDomains);

		List<Domain> result = domainService.findAllNotJoinedYet(anyString());

		assertFalse(result.isEmpty());
		assertTrue(result.contains(domainNotJoined));
		assertFalse(result.contains(domainJoined));
	}

	@Test
	public void shouldReturnUsersThatCanBecomeAdmins() {

		Domain domain = new Domain();
		User admin = new User();
		admin.setId(1L);
		User userJoined = new User();
		userJoined.setId(2L);
		User userJoined2 = new User();
		userJoined2.setId(2L);

		domain.getAdmins().add(admin);
		domain.getUsers().add(userJoined);
		domain.getUsers().add(admin);
		domain.getUsers().add(userJoined2);

		when(domainDao.findById(anyLong())).thenReturn(domain);

		List<User> result = domainService.getUsersWhoCanBecomeAdmins(anyLong());

		assertFalse(result.isEmpty());
		assertTrue(result.contains(userJoined));
		assertTrue(result.contains(userJoined2));
		assertFalse(result.contains(admin));
	}

	@Test
	public void shouldAddAdminToDomain() throws NoObjectFoundException {

		Domain domain = new Domain();
		User admin = new User();
		admin.setId(1L);
		domain.getUsers().add(admin);

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(userDao.findById(1L)).thenReturn(admin);

		Domain persistedDomain = domainService.addAdminToDomain(anyLong(), 1L);

		assertTrue(persistedDomain.getAdmins().contains(admin));
		assertTrue(admin.getDomainsAdmin().contains(persistedDomain));
	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenNoDomainFoundWhenAddingAdmin() throws NoObjectFoundException {

		Domain domain = new Domain();
		User admin = new User();
		admin.setId(1L);
		domain.getUsers().add(admin);

		when(domainDao.findById(anyLong())).thenReturn(null);

		domainService.addAdminToDomain(anyLong(), 1L);
	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenNoUserFoundWhenAddingAdmin() throws NoObjectFoundException {

		Domain domain = new Domain();

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(userDao.findById(anyLong())).thenReturn(null);

		domainService.addAdminToDomain(anyLong(), 1L);
	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenNoDomainFoundWhenDeletingAdmin() throws NoObjectFoundException {

		Domain domain = new Domain();
		User admin = new User();
		admin.setId(1L);
		domain.getUsers().add(admin);

		when(domainDao.findById(anyLong())).thenReturn(null);

		domainService.deleteAdminFromDomain(anyLong(), 1L);
	}

	@Test(expected = NoObjectFoundException.class)
	public void shouldThrowExceptionWhenNoUserFoundWhenDeletingAdmin() throws NoObjectFoundException {

		Domain domain = new Domain();

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(userDao.findById(anyLong())).thenReturn(null);

		domainService.deleteAdminFromDomain(anyLong(), 1L);
	}

	@Test
	public void shouldDeleteAdminFromDomain() throws NoObjectFoundException {

		Domain domain = new Domain();
		User admin = new User();
		admin.setId(1L);
		domain.getUsers().add(admin);
		domain.getAdmins().add(admin);
		admin.getDomains().add(domain);
		admin.getDomainsAdmin().add(domain);

		when(domainDao.findById(anyLong())).thenReturn(domain);
		when(userDao.findById(1L)).thenReturn(admin);

		Domain persistedDomain = domainService.deleteAdminFromDomain(anyLong(), 1L);

		assertFalse(persistedDomain.getAdmins().contains(admin));
		assertFalse(admin.getDomainsAdmin().contains(persistedDomain));
		assertTrue(persistedDomain.getUsers().contains(admin));
		assertTrue(admin.getDomains().contains(persistedDomain));
	}

}

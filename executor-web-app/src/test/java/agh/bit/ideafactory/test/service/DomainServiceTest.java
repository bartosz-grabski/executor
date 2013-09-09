package agh.bit.ideafactory.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;
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
}

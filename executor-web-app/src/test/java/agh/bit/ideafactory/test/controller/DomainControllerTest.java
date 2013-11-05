package agh.bit.ideafactory.test.controller;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.controller.DomainController;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.InstitutionService;
import agh.bit.ideafactory.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class DomainControllerTest {
	
	@Mock
	private UserService userService;

	@Mock
	private DomainService domainService;

	@Mock
	private InstitutionService institutionService;

	@Mock
	private BeanValidator beanValidator;
	
	@InjectMocks
	private DomainController controller;
	
}

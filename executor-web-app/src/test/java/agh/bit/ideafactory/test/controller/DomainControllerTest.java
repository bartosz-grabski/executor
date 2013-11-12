package agh.bit.ideafactory.test.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import agh.bit.ideafactory.controller.DomainController;
import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.helpers.ActiveUserHandlerMethodArgumentResolver;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.InstitutionService;
import agh.bit.ideafactory.service.UserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/mvc-dispatcher-servlet.xml", "classpath:**/spring-security.xml" })
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
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(new ActiveUserHandlerMethodArgumentResolver())
				.build();
	}
	
	@Test
	public void shouldDeleteUserFromDomain() throws Exception {
		
		Domain domain = new Domain();
		final User admin = new User();
		User user1 = new User();
		String adminUsername = "admin";
		admin.setUsername(adminUsername);
		final Long userToDeleteId = 1L;
		final Long idAdmin = 3L;
		final Long domainId = 1L;
		
		bindIdsToUsers(new User[] { admin, user1, }, new Long[] { idAdmin, userToDeleteId} );
		setupDomain(domain, domainId, admin, user1);
		Principal principal = createUserPrincipal(admin);
		
		when(domainService.findById(domainId)).thenReturn(domain);
		when(domainService.isAdminOf(domainId, idAdmin)).thenReturn(true);
		when(userService.getById(userToDeleteId)).thenReturn(user1);
		when(userService.getUserByUserName(adminUsername)).thenReturn(admin);
		when(domainService.deleteUserFromDomain(userToDeleteId, domainId)).thenReturn(true);
		
		mockMvc.perform(delete("/domain/"+domainId+"/user/"+userToDeleteId).principal(principal))
			.andExpect(status().isOk());
		
		verify(domainService).deleteUserFromDomain(domainId, userToDeleteId);
		
	}
	
	@Test
	public void shouldGiveStatus404WhenUserDoesNotExist() throws Exception {
		final User admin = new User();
		final Long userToDeleteId = 1L;
		final Long idAdmin = 3L;
		final Long domainId = 1L;
		String adminUsername = "admin";
		admin.setUsername(adminUsername);
		admin.setId(idAdmin);
		Principal principal = createUserPrincipal(admin);
		
		when(userService.getUserByUserName(adminUsername)).thenReturn(admin);
		when(domainService.isAdminOf(domainId, idAdmin)).thenReturn(true);
		when(domainService.deleteUserFromDomain(userToDeleteId, domainId)).thenThrow(new NoObjectFoundException(User.class));
		
		
		mockMvc.perform(delete("/domain/"+domainId+"/user/"+userToDeleteId).principal(principal))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldGiveStatus403WhenTryingToDeleteOtherAdmin() throws Exception {
		final User admin = new User();
		final Long userToDeleteId = 1L;
		final Long idAdmin = 3L;
		final Long domainId = 1L;
		String adminUsername = "admin";
		admin.setUsername(adminUsername);
		admin.setId(idAdmin);
		Principal principal = createUserPrincipal(admin);
		
		when(userService.getUserByUserName(adminUsername)).thenReturn(admin);
		when(domainService.isAdminOf(domainId, idAdmin)).thenReturn(true);
		when(domainService.deleteUserFromDomain(userToDeleteId, domainId)).thenThrow(new UnsupportedOperationException("Lack of priviliges"));
		
		mockMvc.perform(delete("/domain/"+domainId+"/user/"+userToDeleteId).principal(principal))
				.andExpect(status().isForbidden());
	}
	
	
	private void bindIdsToUsers(User[] users, Long[] ids) {
		for (int i = 0; i < users.length; i++) {
			users[i].setId(ids[i]);
		}
	}
	
	private void setupDomain(Domain domain, Long domainId, User admin, User... users) {
		domain.setId(domainId);
		
		domain.getUsers().add(admin);
		domain.getAdmins().add(admin);
		
		admin.getDomains().add(domain);
		admin.getDomainsAdmin().add(domain);
		
		for(User u : users) {
			u.getDomains().add(domain);
			domain.getUsers().add(u);
		}
	}
	
	private Principal createUserPrincipal(User user) {
		final String username = user.getUsername();
		return new Principal() {
			@Override
			public String getName() {
				return username;
			}
		};
	}
	
	
}
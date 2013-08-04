package agh.bit.ideafactory.test.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-db.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(propagation = Propagation.NEVER)
public abstract class AbstractDaoTest extends AbstractTest {

	protected static final Long ALL_SUBMITS_COUNT = 3L;
	protected static final Long ALL_PROBLEMS_COUNT = 3L;
	protected static final Long ALL_USERS_COUNT = 3L;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserService userService;

	@Before
	public void setUp() {
		try {
			clearDatabase(dataSource);
			initDB(dataSource);
			initSpringSecurityContext("admin");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	// ********************* HELPERS ************

	// private final Random random = new Random(new Date().getTime());

	protected void initSpringSecurityContext(String userName) {
		MockHttpServletRequest request = new MockHttpServletRequest();

		UserDetails user = userService.getUserByUserName(userName);

		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	protected Date getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	protected Date getDateInFuture() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	protected Date getDate(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);

		return cal.getTime();
	}

	protected Date getDateWithoutTime(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	protected Authority getAuthorityByName(String name) {
		Authority result = new Authority();
		switch (name) {
		case "ROLE_ADMIN":
			result.setAuthority("ROLE_ADMIN");
			result.setId(1L);
			break;
		case "ROLE_USER":
			result.setAuthority("ROLE_USER");
			result.setId(2L);
			break;
		case "ROLE_INSTITUTION":
			result.setAuthority("ROLE_INSTITUTION");
			result.setId(3L);
			break;
		default:
			return null;
		}
		return result;
	}

	protected Problem returnNewCompleteProblem() {
		Problem problem = new Problem();
		long id = ALL_PROBLEMS_COUNT + 1;
		problem.setId(ALL_PROBLEMS_COUNT + 1);
		problem.setFilePath("ProblemContent " + id);
		problem.setName("ProblemName " + id);
		problem.setExercises(new ArrayList<Exercise>());
		return problem;
	}

}

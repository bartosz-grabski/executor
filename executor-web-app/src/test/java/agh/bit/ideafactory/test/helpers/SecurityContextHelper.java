package agh.bit.ideafactory.test.helpers;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import agh.bit.ideafactory.model.User;

public class SecurityContextHelper {

	public static void initSpringSecurityContext(String userName) {
		MockHttpServletRequest request = new MockHttpServletRequest();

		User user = new User();
		user.setUsername(userName);

		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}

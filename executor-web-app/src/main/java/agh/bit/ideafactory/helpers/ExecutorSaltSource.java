package agh.bit.ideafactory.helpers;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class ExecutorSaltSource implements SaltSource {

	private static final String SALT_SOURCE = "e72e9bda-84e9-49ee-a19e-79e1c93fd47c"; // generated with java.util.UUID

	@Override
	public Object getSalt(UserDetails user) {
		// TODO Auto-generated method stub
		return SALT_SOURCE;
	}
	
	public static String getSalt() {
		return SALT_SOURCE;
	}

}

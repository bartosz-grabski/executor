package agh.bit.ideafactory.helpers;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import agh.bit.ideafactory.model.Authority;

public class AuthoritiesHelper {

	public static boolean isAuthorityGranted(String authorityName) {
		Collection<? extends GrantedAuthority> collection = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : collection) {
			if (grantedAuthority.getAuthority().equals(authorityName)) {
				return true;
			}
		}
		return false;

	}

}

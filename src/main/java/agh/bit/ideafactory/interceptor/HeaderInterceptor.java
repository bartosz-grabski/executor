package agh.bit.ideafactory.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import agh.bit.ideafactory.service.UserService;

public class HeaderInterceptor  extends HandlerInterceptorAdapter{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

		
		
		if (modelAndView != null) {
			if ( SecurityContextHolder.getContext().getAuthentication() != null && 
					SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			    String userName = auth.getName();
			    if ( !userName.equals("anonymousUser") ) {
			    	modelAndView.getModelMap().addAttribute("username",userName);
			    }
			}
			
		}		
	}
	
	
}

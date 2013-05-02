package agh.bit.ideafactory.controller;

import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;


@Controller 
public class RegisterController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String main(ModelMap model) {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ModelMap model, HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashed = passwordEncoder.encodePassword(password,username);
		User user = new User();
        user.setUsername(username);
        user.setPassword(hashed);
        user.setEnabled(true);
        try {
            userService.addUser(user);
        } catch (Exception e) {
            model.addAttribute("error", "true");
        }
        System.out.println(user.getId());
        model.addAttribute("registered", "true");
		return "register";
	}
	

}

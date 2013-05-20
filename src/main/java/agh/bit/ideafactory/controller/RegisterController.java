package agh.bit.ideafactory.controller;

import agh.bit.ideafactory.helpers.TokenGenerator;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.AuthorityService;
import agh.bit.ideafactory.service.MailService;
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
import java.util.ArrayList;
import java.util.HashSet;


@Controller 
public class RegisterController {
    String active = "register";

    @Autowired
    private UserService userService;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenGenerator tokenGenerator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String main(ModelMap model) {
        model.addAttribute("active", active);
		return "home/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ModelMap model, HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String hashed = passwordEncoder.encodePassword(password,username);
		User user = new User();
        user.setUsername(username);
        user.setPassword(hashed);
        user.setEmail(email);
        user.setEnabled(true);

        try {
            userService.addUser(user);
            String token = tokenGenerator.generateToken();
            mailService.sendMail("from@from.pl",user.getEmail(),
                    "registered",token);
            model.addAttribute("registered", "true");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "true");
        }
        model.addAttribute("active", active);

		return "home/register";
	}
	

}

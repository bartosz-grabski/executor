package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    String active = "login";


	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
        model.addAttribute("active",active);
        return "home/login";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginFailed(ModelMap model) {
		
		model.addAttribute("error","true");
        model.addAttribute("active",active);
		return "home/login";
		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
        model.addAttribute("active",active);
		return "home/login";
	}
	

	
}
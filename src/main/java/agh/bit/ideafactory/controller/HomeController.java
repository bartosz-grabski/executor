package agh.bit.ideafactory.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
	
	@RequestMapping(value={"/","/home"})
	public String welcome(ModelMap model, Principal principal) {
        if (principal != null) {
            String name = principal.getName();
            model.addAttribute("username", name);
        }
		return "home/home";
	}

}

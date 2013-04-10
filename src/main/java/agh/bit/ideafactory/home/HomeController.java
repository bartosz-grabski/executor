package agh.bit.ideafactory.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value="/")
	public String welcome(ModelMap model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Welcome!");
		return "home";
	}

}

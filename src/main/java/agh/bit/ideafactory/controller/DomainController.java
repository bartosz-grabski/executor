package agh.bit.ideafactory.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DomainController {

	@RequestMapping(value = "/domain")
	public String domain(ModelMap model, Principal principal) {
		String user = principal.getName();
		model.addAttribute("user", user);
		return "home/domain";
	}
	
}

package agh.bit.ideafactory.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class DomainController {

	@Autowired
	private UserService userService;

	@Autowired
	private DomainService domainService;

	@RequestMapping(value = "/domain/list")
	public String domain(ModelMap model, Principal principal) {
		// User user = userService.getUserByUserNameFetched(principal.getName());
		List<Domain> domains = domainService.getDomainsByAdminName(principal.getName());
		model.addAttribute("domains", domains);
		return "domain/home";
	}

	@RequestMapping(value = "/domain/create")
	public String createDomain(ModelMap model, Principal principal) {

		return "das";
	}

}

package agh.bit.ideafactory.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.InstitutionService;

/**
 * @author bgrabski
 */
@Controller
public class InstitutionController {

	@Autowired
	private InstitutionService institutionService;

	@Autowired
	private DomainService domainService;

	@RequestMapping(value = "/business/home", method = RequestMethod.GET)
	public String home() {
		return "institution/home";
	}

	@RequestMapping(value = "business/domains", method = RequestMethod.GET)
	private String listDomains(ModelMap model, Principal principal) {

		Institution institution = institutionService.getInstitutionByEmail(principal.getName());

		model.addAttribute("domains", institution.getDomains());

		return "institution/domains";
	}
}

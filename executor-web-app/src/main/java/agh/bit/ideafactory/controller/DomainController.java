package agh.bit.ideafactory.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.helpers.AuthoritiesHelper;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.InstitutionService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class DomainController {

	@Autowired
	private UserService userService;

	@Autowired
	private DomainService domainService;

	@Autowired
	private InstitutionService institutionService;

	@Autowired
	private BeanValidator beanValidator;

	@RequestMapping(value = "/domain/list")
	public String domainList(ModelMap model, Principal principal) {
		List<Domain> domains;
		if (AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION")) {
			Institution institution = institutionService.getInstitutionByEmail(principal.getName());
			domains = institution.getDomains();
		} else {
			domains = domainService.getDomainsByAdminName(principal.getName());
		}
		model.addAttribute("domains", domains);
		model.addAttribute("domain", new Domain());
		return "domain/list";
	}

	@RequestMapping(value = "/domain/create")
	public String createDomain(@ModelAttribute("domain") Domain domain, ModelMap model, Principal principal, BindingResult bindingResult) {

		if (AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION")) {
			Institution institution = institutionService.getInstitutionByEmail(principal.getName());
			if (institution != null) {

				beanValidator.validate(domain, bindingResult);

				if (!bindingResult.hasErrors()) {
					domain.setInstitution(institution);
					try {
						domainService.create(domain, institution);
						institution.getDomains().add(domain);
					} catch (NotUniquePropertyException e) {
						bindingResult.rejectValue(e.getPropertyName(), " ", e.getMessage());
					}

				}
				model.addAttribute("domains", institution.getDomains());
			}
		}
		return "domain/list";
	}
}

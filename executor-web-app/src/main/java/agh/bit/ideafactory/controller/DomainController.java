package agh.bit.ideafactory.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.MapUtils;

import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.AuthoritiesHelper;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.helpers.ModelMapUtils;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.model.User;
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

	@RequestMapping(value = "/domain/list", method = RequestMethod.GET)
	public String domainList(ModelMap model, Principal principal) {
		List<Domain> domainsAdministrated;
		if (AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION")) {
			Institution institution = institutionService.getInstitutionByEmail(principal.getName());
			domainsAdministrated = institution.getDomains();
		} else {
			User user = userService.getUserWithDomains(principal.getName());

			domainsAdministrated = user.getDomainsAdmin();
			List<Domain> domainsJoined = user.getDomains();
			model.addAttribute("domainsJoined", domainsJoined);
		}
		model.addAttribute("domainsAdministrated", domainsAdministrated);
		model.addAttribute("domain", new Domain());
		return "domain/list";
	}

	@RequestMapping(value = "/domain/create", method = RequestMethod.POST)
	public String createDomain(@ModelAttribute("domain") Domain domain, ModelMap model, Principal principal, BindingResult bindingResult) {

		if (AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION")) {
			Institution institution = institutionService.getInstitutionByEmail(principal.getName());
			if (institution != null) {

				beanValidator.validate(domain, bindingResult);

				if (!bindingResult.hasErrors()) {
					try {
						domainService.create(domain, institution);
						institution.getDomains().add(domain);
						ModelMapUtils.setSuccess(model, "Domain created succesfully!");
					} catch (NotUniquePropertyException e) {
						bindingResult.rejectValue(e.getPropertyName(), " ", e.getMessage());
						ModelMapUtils.setError(model, "Errors occured during domain creation");
					}
				} else {
					ModelMapUtils.setError(model, "Errors occured during domain creation");
				}
				model.addAttribute("domainsAdministrated", institution.getDomains());
			}
		}
		return "domain/list";
	}

	@RequestMapping(value = "domain/details", method = RequestMethod.GET)
	public String getDomainDetails(@RequestParam("domainId") Long domainId, ModelMap map) {

		Domain domain = domainService.findByIdFetched(domainId);

		map.addAttribute("domain", domain);
		map.addAttribute("group", new Group());
		return "domain/details";
	}

	@RequestMapping(value = "domain/join", method = RequestMethod.GET)
	public String joinForm(ModelMap map) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Domain> domains = domainService.findAllNotJoinedYet(username);

		map.addAttribute("domains", domains);

		return "domain/join";
	}

	@RequestMapping(value = "domain/joinDomain", method = RequestMethod.POST)
	public String join(@RequestParam("domainId") Long domainId, @RequestParam("domainPassword") String domainPassword, ModelMap map) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		try {
			domainService.joinDomain(domainId, domainPassword, username);
			ModelMapUtils.setSuccess(map, "Successfuly joined domain!");
		} catch (PasswordMatchException e) {
			ModelMapUtils.setError(map, e.getMessage());
		}

		List<Domain> domains = domainService.findAllNotJoinedYet(username);

		map.addAttribute("domains", domains);

		return "domain/join";
	}

	@RequestMapping(value = "domain/manageAdmins", method = RequestMethod.GET)
	public String manageAdmins(@RequestParam("domainId") Long domainId, ModelMap map) {

		Domain domain = domainService.findByIdFetched(domainId);

		List<User> usersToBecomeAdmins = domainService.getUsersWhoCanBecomeAdmins(domain.getId());
		map.addAttribute("usersToBecomeAdmins", usersToBecomeAdmins);

		map.addAttribute("domain", domain);

		return "domain/manage_admins";
	}

}

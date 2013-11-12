package agh.bit.ideafactory.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import agh.bit.ideafactory.annotations.ActiveUser;
import agh.bit.ideafactory.exception.NoObjectFoundException;
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
	public String getDomainDetails(@RequestParam("domainId") Long domainId, ModelMap map, HttpServletRequest request) {

		Domain domain = domainService.findByIdFetched(domainId);

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		ModelMapUtils.addFlashAttributesToModelMap(map, request);

		boolean canCreateGroup = domainService.canCreateGroup(domainId, username) || AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION");

		map.addAttribute("canCreateGroup", canCreateGroup);
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
	public String manageAdmins(@RequestParam("domainId") Long domainId, ModelMap map, HttpServletRequest request) {

		Domain domain = domainService.findByIdFetched(domainId);

		if (domain != null) {
			List<User> usersToBecomeAdmins = domainService.getUsersWhoCanBecomeAdmins(domain.getId());
			map.addAttribute("usersToBecomeAdmins", usersToBecomeAdmins);

		}

		ModelMapUtils.addFlashAttributesToModelMap(map, request);

		map.addAttribute("domain", domain);

		return "domain/manage_admins";
	}

	@RequestMapping(value = "domain/addAdmin", method = RequestMethod.POST)
	public String addAdmin(@RequestParam("domainId") Long domainId, @RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {

		try {
			domainService.addAdminToDomain(domainId, userId);
			ModelMapUtils.setRedirectSuccess(redirectAttributes, "Admin added succesfully!");
		} catch (NoObjectFoundException e) {
			ModelMapUtils.setRedirectError(redirectAttributes, "Admin added unsuccesfully - no domain or user found!");
		}

		redirectAttributes.addAttribute("domainId", domainId);

		return "redirect:/domain/manageAdmins";

	}

	@RequestMapping(value = "domain/deleteAdmin", method = RequestMethod.GET)
	public String deleteAdmin(@RequestParam("domainId") Long domainId, @RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {

		try {
			domainService.deleteAdminFromDomain(domainId, userId);
			ModelMapUtils.setRedirectSuccess(redirectAttributes, "Admin deleted succesfully!");
		} catch (NoObjectFoundException e) {
			ModelMapUtils.setRedirectError(redirectAttributes, "Admin deleted unsuccesfully - no domain or user found!");
		}

		redirectAttributes.addAttribute("domainId", domainId);

		return "redirect:/domain/manageAdmins";
	}
	
	@RequestMapping(value = "domain/{domainId}/user/{userId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteUser(@PathVariable Long domainId, @PathVariable Long userId, @ActiveUser String username, HttpServletRequest request) throws NoObjectFoundException {
		User callingUser = userService.getUserByUserName(username);
		if (!domainService.isAdminOf(domainId, callingUser.getId())) throw new UnsupportedOperationException("Cannot delete users from domains that you do not own!");
		domainService.deleteUserFromDomain(userId, domainId);
		
	}
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoObjectFoundException.class)
	public void NoObjectFound(HttpServletRequest request, Exception e) {
	}
	
	@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "Lack of permissions!")
	@ExceptionHandler(UnsupportedOperationException.class)
	public void UnsupportedOperation(HttpServletRequest request, Exception e) {
	}

}

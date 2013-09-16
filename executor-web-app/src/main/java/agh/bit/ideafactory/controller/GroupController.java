package agh.bit.ideafactory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.helpers.AuthoritiesHelper;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.helpers.ModelMapUtils;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.GroupService;

@Controller
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private DomainService domainService;

	@Autowired
	private BeanValidator beanValidator;

	@RequestMapping(value = "group/list", method = RequestMethod.GET)
	public String listGroups(@RequestParam("domainId") final Long domainId, ModelMap map) {

		List<Group> groups = null;
		if (AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION")) {
			groups = groupService.getGroupsByDomain(domainId);

		} else {

		}

		map.addAttribute("groups", groups);

		return "sad";
	}

	@RequestMapping(value = "group/create", method = RequestMethod.POST)
	public String createGroup(@ModelAttribute("group") Group group, @RequestParam("domainId") Long domainId, ModelMap map, BindingResult bindingResult, HttpServletRequest request) {

		Domain domain = domainService.findByIdFetched(domainId);
		if (domain != null) {

			beanValidator.validate(group, bindingResult);

			if (!bindingResult.hasErrors()) {
				try {
					groupService.save(group, domain);
					ModelMapUtils.setSuccess(map, "Group created succesfully!");
				} catch (NotUniquePropertyException e) {
					ModelMapUtils.setError(map, "Errors occured during group creation");
					e.printStackTrace();
				}
			} else {
				ModelMapUtils.setError(map, "Errors occured during group creation");
			}
			map.addAttribute("domain", domain);
		}

		return "domain/details";
	}
}

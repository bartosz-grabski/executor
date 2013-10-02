package agh.bit.ideafactory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.helpers.ModelMapUtils;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.DomainService;
import agh.bit.ideafactory.service.GroupService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private DomainService domainService;

	@Autowired
	private BeanValidator beanValidator;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/group/create", method = RequestMethod.POST)
	public String createGroup(@ModelAttribute("group") Group group, @RequestParam("domainId") Long domainId, ModelMap map, BindingResult bindingResult) {

		Domain domain = domainService.findByIdFetched(domainId);
		if (domain != null) {

			beanValidator.validate(group, bindingResult);

			if (!bindingResult.hasErrors()) {
				try {
					groupService.save(group, domain);
					ModelMapUtils.setSuccess(map, "Group created succesfully!");
				} catch (NotUniquePropertyException e) {
					bindingResult.rejectValue(e.getPropertyName(), " ", e.getMessage());
					ModelMapUtils.setError(map, "Errors occured during group creation");
				}
			} else {
				ModelMapUtils.setError(map, "Errors occured during group creation");
			}
			map.addAttribute("domain", domain);
		}

		return "domain/details";
	}

	@RequestMapping(value = "/group/details", method = RequestMethod.GET)
	public String getGroupDetails(@RequestParam("groupId") Long groupId, ModelMap map) {

		Group group = groupService.findByIdFetched(groupId);
		map.addAttribute("group", group);

		return "group/details";
	}

	@RequestMapping(value = "/group/join", method = RequestMethod.GET)
	public String joinGroupForm(@RequestParam("groupId") Long groupId, ModelMap map) {

		Group group = groupService.findById(groupId);

		map.addAttribute("group", group);

		User user = userService.getUserByUserNameFetched(SecurityContextHolder.getContext().getAuthentication().getName());

		if (user.getGroups().contains(group)) {
			map.addAttribute("canJoin", false);
		} else {
			map.addAttribute("canJoin", true);
		}

		return "group/join";
	}

	@RequestMapping(value = "/group/joinGroup", method = RequestMethod.POST)
	public String joinGroup(@RequestParam("groupId") Long groupId, @RequestParam("groupPassword") String groupPassword, ModelMap map) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		Group group = null;

		try {
			group = groupService.joinGroup(groupId, userName, groupPassword);

			if (group != null) {
				ModelMapUtils.setSuccess(map, "Group joined succesfuly!");
			} else {
				ModelMapUtils.setError(map, "No such group found!");
			}
		} catch (PasswordMatchException e) {
			ModelMapUtils.setError(map, e.getMessage());
			map.addAttribute("canJoin", true);
			group = groupService.findById(groupId);
		}

		map.addAttribute("group", group);

		return "/group/join";
	}

}

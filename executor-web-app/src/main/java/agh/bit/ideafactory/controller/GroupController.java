package agh.bit.ideafactory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import agh.bit.ideafactory.helpers.AuthoritiesHelper;
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

	@RequestMapping(value = "group/list", method = RequestMethod.GET)
	public String listGroups(@RequestParam("domainId") final Long domainId, ModelMap map) {

		List<Group> groups;
		if (AuthoritiesHelper.isAuthorityGranted("ROLE_INSTITUTION")) {
			// Domain domain = domainService.
		} else {

		}

		return "sad";
	}

	@RequestMapping(value = "group/create", method = RequestMethod.POST)
	public String createGroup(@ModelAttribute("group") Group group, ModelMap map, BindingResult bindingResult) {
		return "ads";
	}
}

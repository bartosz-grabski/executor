package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import agh.bit.ideafactory.helpers.ModelMapUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bgrabski Class for handling request to create InstitutionAccount
 */
@Controller
public class InstitutionRegisterController {

    private static final String viewName = "home/institution_register";
	/**
	 * Simply returns the view
	 * 
	 * @return String representation of the associated view
	 */
	@RequestMapping(value = "/business/register", method = RequestMethod.GET)
	public String registerGet(ModelMap modelMap, HttpServletRequest request) {
		return viewName;
	}

    @RequestMapping(value = "/business/register", method = RequestMethod.POST)
    public String registerPost() {
        return viewName;
    }

}

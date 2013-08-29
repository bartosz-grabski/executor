package agh.bit.ideafactory.controller;

import static agh.bit.ideafactory.helpers.ModelMapUtils.setError;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bgrabski
 */
@Controller
public class InstitutionLoginController {

	private final String VIEW_NAME = "institution/institution_login";

	@RequestMapping(value = "/business/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return VIEW_NAME;
	}

	@RequestMapping(value = "/business/loginfailed", method = RequestMethod.GET)
	public String loginfailed(ModelMap model) {
		setError(model, "true");
		return VIEW_NAME;
	}
}
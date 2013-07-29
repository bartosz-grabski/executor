package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bgrabski
 */
@Controller
public class InstitutionConsoleController {

	private final String viewName = "institution/institution_console";

	@RequestMapping(value = "/business/console", method = RequestMethod.GET)
	public String console() {
		return viewName;
	}
}

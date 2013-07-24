package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bgrabski Class for handling request to create InstitutionAccount
 */
@Controller
public class InstitutionRegisterController {

	/**
	 * Simply returns the view
	 * 
	 * @return String representation of the associated view
	 */
	@RequestMapping(value = "/business/register", method = RequestMethod.GET)
	public String register() {
		return "home/dupa";
	}

}

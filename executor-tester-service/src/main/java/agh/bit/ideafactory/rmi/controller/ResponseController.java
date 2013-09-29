package agh.bit.ideafactory.rmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import agh.bit.ideafactory.rmi.model.TesterOutput;
import agh.bit.ideafactory.rmi.services.ResultService;

/**
 * Controller class for handling incoming request from tester module. It contains only one method for incoming requests
 * 
 * @author bgrabski
 * 
 */
@Controller
public class ResponseController {

	@Autowired
	private ResultService resultService;

	/**
	 * Method for handling requests form tester to update test's results
	 * 
	 * @param output - output mapped from JSON object to Java model class
	 *            
	 * @return "OK" if not interrupted
	 */
	@RequestMapping(value = "/output", method = RequestMethod.POST)
	public @ResponseBody
	String acceptTesterResponse(@RequestBody TesterOutput output) {
		resultService.putResult(output);
		return "OK";
	}

}

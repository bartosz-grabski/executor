package agh.bit.ideafactory.rmi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import agh.bit.ideafactory.rmi.model.TesterOutput;
import agh.bit.ideafactory.utils.DatabaseConnectionUtil;
import agh.bit.ideafactory.utils.TestResultConverterUtil;

/**
 * Controller class for handling incoming request from tester module. It contains only one method for incoming requests
 * 
 * @author bgrabski
 * 
 */
@Controller
public class ResponseController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);

	/**
	 * Method for handling requests form tester to update test's results
	 * 
	 * @param output
	 *            - output mapped from JSON object to POJO
	 * @param submitId
	 *            - submit id passed as String from tester
	 * @return "OK" if not interrupted
	 */
	@RequestMapping(value = "/{submit_id}", method = RequestMethod.POST)
	public @ResponseBody
	String acceptTesterResponse(@RequestBody TesterOutput output, @PathVariable("submit_id") String submitId) {
		logger.debug("Controller invoked for submit_id=" + submitId);
		DatabaseConnectionUtil dbConnection = new DatabaseConnectionUtil();
		dbConnection.putResult(TestResultConverterUtil.convert(output.getTestResult()), submitId);
		logger.debug("Controller finished");
		return "OK";
	}

}

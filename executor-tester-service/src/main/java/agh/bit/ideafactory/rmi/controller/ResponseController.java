package agh.bit.ideafactory.rmi.controller;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResponseController {
	
	@RequestMapping(value = "/response", method=RequestMethod.POST)
	public String acceptTesterResponse(HttpRequest request) {
		getResponse();
		return "OK";
	}
	
	private String getResponse() {
		return null;
	}

}

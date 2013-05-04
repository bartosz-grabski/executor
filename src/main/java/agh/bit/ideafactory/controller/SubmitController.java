package agh.bit.ideafactory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import agh.bit.ideafactory.service.SubmitService;

@Controller
public class SubmitController {

	@Autowired
	private SubmitService submitService;
	
	@RequestMapping("/submit/send")
	public String sendSubmit(ModelMap model) {
		
		
		return "submit/send";
	}
	
	
}

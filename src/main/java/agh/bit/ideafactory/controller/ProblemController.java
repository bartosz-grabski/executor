package agh.bit.ideafactory.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ProblemService;

@Controller
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	@RequestMapping(value="/problem")
	public String problem(ModelMap model) {
		
		List<Problem> problemy = problemService.getProblems();
		model.addAttribute("problemList", problemy);
		for ( Problem p : problemy) 
			System.out.println(p.getId());
		return "problem/list";
	}
	
	@RequestMapping(value="/problem/list")
	public String listProblems(ModelMap model) {
		
		return "problem/list";
	}
	
	
}

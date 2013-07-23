package agh.bit.ideafactory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/problem", "problem/list" })
	public String listProblems(ModelMap model) {

		List<Problem> problems = problemService.getProblems();
		model.addAttribute("problemList", problems);
		return "problem/list";
	}

	@RequestMapping(value = "problem/details", method = RequestMethod.GET)
	public String showProblem(ModelMap model, @RequestParam("id") Long id) {

		Problem problem = problemService.getById(id);
		User user = null;
		if (problem != null) {
			System.out.println(problem.toString());
			user = problem.getUser();
			model.addAttribute("user", user);
		}
		model.addAttribute("problem", problem);

		return "problem/details";

	}

}

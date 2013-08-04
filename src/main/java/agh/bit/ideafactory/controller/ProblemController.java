package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.helpers.FileUploadForm;
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

	@RequestMapping(value = { "/problem", "/problem/list" }, method = RequestMethod.GET)
	public String listProblems(ModelMap model) {

		List<Problem> problems = problemService.getProblems();
		model.addAttribute("problemList", problems);
		return "problem/send";
	}

	@RequestMapping(value = "problem/details", method = RequestMethod.GET)
	public String showProblem(ModelMap model, @RequestParam("id") Long id) {

		Problem problem = problemService.getById(id);
		model.addAttribute("problem", problem);
		model.addAttribute("tests", problem.getTests());

		return "problem/details";

	}

	@RequestMapping(value = "problem/send", method = RequestMethod.POST)
	public String saveProblem(@ModelAttribute("fileUploadForm") final FileUploadForm uploadForm, @RequestParam(value = "problemFile") final MultipartFile problemFile,
			@RequestParam("problemTitle") final String problemTitle, ModelMap model, Principal principal) throws IOException {
		List<MultipartFile> problemTestSet = uploadForm.getFiles();

		User user = userService.getUserByUserNameFetched(principal.getName());
		problemService.saveProblemOnServer(problemFile, problemTestSet, user, problemTitle);

		List<Problem> problems = problemService.getProblems();
		model.addAttribute(problems);

		return "problem/send";
	}

}

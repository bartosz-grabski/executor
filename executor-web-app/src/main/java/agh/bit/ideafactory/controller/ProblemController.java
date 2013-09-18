package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.service.TestService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class ProblemController {

	@Autowired
	private TestService testService;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/problem", "/problem/list" }, method = RequestMethod.GET)
	public String listProblems(ModelMap model) {

		List<Problem> problems = problemService.getProblems();
		model.addAttribute("problemList", problems);
		return "problem/list";
	}

	@RequestMapping(value = "problem/details", method = RequestMethod.GET)
	public String showProblem(ModelMap model, @RequestParam("id") Long id) {

		Problem problem = problemService.getById(id);
		List<Test> tests = testService.getTestsByProblem(problem);
		model.addAttribute("problem", problem);
		model.addAttribute("tests", tests);
		return "problem/details";

	}

	@RequestMapping(value = "problem/upload", method = RequestMethod.GET)
	public String uploadProblem(ModelMap model) {
		return "problem/upload";
	}

	@RequestMapping(value = "problem/upload", method = RequestMethod.POST)
	public String saveProblem(@ModelAttribute("fileUploadForm") final FileUploadForm uploadForm, @RequestParam(value = "problemFile") final MultipartFile problemFile,
			@RequestParam("problemTitle") final String problemTitle, ModelMap model, Principal principal) throws IOException {
		List<MultipartFile> problemTestSet = uploadForm.getFiles();
		User user = userService.getUserByUserNameFetched(principal.getName());
		problemService.saveProblemOnServer(problemFile, problemTestSet, user, problemTitle);

		List<Problem> problems = problemService.getProblems();
		model.addAttribute(problems);

		return "problem/upload";
	}

	@RequestMapping(value = "problem/download", method = RequestMethod.GET)
	public String downloadProblem(@RequestParam("id") Long problemID, HttpServletResponse response) {

		Problem problem = problemService.getById(problemID);
		try {
			OutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			out.write(problem.getContent());
			out.flush();
			out.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

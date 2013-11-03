package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import agh.bit.ideafactory.exception.FileExtensionException;
import agh.bit.ideafactory.exception.ResourceNotFoundException;
import agh.bit.ideafactory.form.FileUploadForm;
import agh.bit.ideafactory.helpers.ModelMapUtils;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ExerciseService;
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
	private ExerciseService exerciseService;

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/problem", "/problem/list" }, method = RequestMethod.GET)
	public String listProblems(ModelMap model) {

		List<Problem> problems = problemService.getProblems(true);
		model.addAttribute("problemList", problems);
		return "problem/list";
	}

	@RequestMapping(value = "problem/details", method = RequestMethod.GET)
	public String showProblem(ModelMap model, @RequestParam("problemId") Long problemId, HttpServletRequest request) {

		Problem problem = problemService.getById(problemId);
		List<Test> tests = testService.getTestsByProblem(problem);
		model.addAttribute("problem", problem);
		model.addAttribute("tests", tests);
		model.addAttribute("problemContent", new String(problem.getContent()));
		ModelMapUtils.addFlashAttributesToModelMap(model, request);
		return "problem/details";
	}

	@RequestMapping(value = "problem/details", method = RequestMethod.POST)
	public String addTestToProblem(ModelMap model, @ModelAttribute("fileUploadForm") final FileUploadForm uploadForm, @RequestParam("problemId") Long problemId, RedirectAttributes redirectAttributes) {

		List<MultipartFile> problemTestSet = uploadForm.getFiles();
		Problem problem = problemService.getById(problemId);
		try {
			problemService.addTestsToProblem(problemId, problemTestSet);
			ModelMapUtils.setRedirectSuccess(redirectAttributes, "Test has been added succesfully!");
		} catch (FileExtensionException e) {
			ModelMapUtils.setRedirectError(redirectAttributes, "Wrong file extension. TXT required.");
		} catch (IOException e) {
			ModelMapUtils.setRedirectError(redirectAttributes, "Cannot add test.");
		}

		redirectAttributes.addAttribute("problemId", problemId);
		return "redirect:/problem/details";
	}

	@RequestMapping(value = "problem/upload", method = RequestMethod.GET)
	public String uploadProblem(ModelMap map) {

		return "problem/upload";
	}

	@RequestMapping(value = "problem/upload", method = RequestMethod.POST)
	public String saveProblem(@ModelAttribute("fileUploadForm") final FileUploadForm uploadForm, @RequestParam(value = "problemFile") final MultipartFile problemFile,
			@RequestParam("problemTitle") final String problemTitle, ModelMap model, Principal principal, RedirectAttributes redirectAttributes) throws IOException {

		List<MultipartFile> problemTestSet = uploadForm.getFiles();

		try {
			User user = userService.getUserByUserNameFetched(principal.getName());
			problemService.saveProblemOnServer(problemFile, problemTestSet, user, problemTitle);
			ModelMapUtils.setRedirectSuccess(redirectAttributes, "Problem created succesfully!");
		} catch (FileExtensionException e) {
			ModelMapUtils.setRedirectError(redirectAttributes, "Wrong file extension, TXT required!");
		}

		return "redirect:/problem/upload";
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
			logger.error("Downloading file has failed.", e);
		}

		return null;
	}

	@RequestMapping(value = "/problem/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteProblem(@PathVariable Long id, @RequestParam("keep_history") boolean keepHistory, ModelMap modelMap) {
		Problem problem = problemService.getById(id);

		if (problem == null) {
			throw new ResourceNotFoundException();
		}

		problemService.deleteProblem(problem, keepHistory);

	}

}

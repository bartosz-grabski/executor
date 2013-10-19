package agh.bit.ideafactory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import agh.bit.ideafactory.form.ExerciseForm;
import agh.bit.ideafactory.helpers.BeanValidator;
import agh.bit.ideafactory.helpers.ModelMapUtils;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ExerciseService;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private BeanValidator beanValidator;

	@RequestMapping(value = { "/exercise", "exercise/list" })
	public String listExercses(ModelMap modelMap) {

		List<Exercise> exercises = exerciseService.findAll();
		modelMap.addAttribute("exerciseList", exercises);
		return "exercise/list";
	}

	@RequestMapping(value = "/exercise/details", method = RequestMethod.GET)
	public String showExercise(ModelMap modelMap, @RequestParam(value = "id", required = true) final Long id) {

		Exercise exercise = exerciseService.getById(id);
		modelMap.addAttribute("exercise", exercise);
		return "exercise/details";

	}

	@RequestMapping(value = "/exercise/create", method = RequestMethod.GET)
	public String createExerciseForm(@ModelAttribute("exercise") ExerciseForm exerciseForm, @RequestParam("problemId") Long problemId, ModelMap map, HttpServletRequest request) {

		ModelMapUtils.addFlashAttributesToModelMap(map, request);
		ModelMapUtils.addBindingResultToModelMap(map);

		// map.put("exercise", exerciseForm);
		return "exercise/create";
	}

	@RequestMapping(value = "/exercise/create", method = RequestMethod.POST)
	public String createExercise(@ModelAttribute("exercise") ExerciseForm exerciseForm, BindingResult bindingResult, @RequestParam("problemId") Long problemId, RedirectAttributes redirectAttributes) {

		Problem problem = problemService.getById(problemId);

		if (problem != null) {

			redirectAttributes.addAttribute("problemId", problemId);

			Exercise exercise = exerciseForm.createExercise();

			beanValidator.validate(exercise, bindingResult);

			if (!bindingResult.hasErrors()) {

			} else {
				ModelMapUtils.setRedirectBindingResult("exercise", bindingResult, redirectAttributes);
				ModelMapUtils.setRedirectError(redirectAttributes, "Errors creating exercise!");
			}

		}

		return "redirect:/exercise/create";
	}
}

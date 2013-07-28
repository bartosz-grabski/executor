package agh.bit.ideafactory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.service.ExerciseService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private UserService userService;

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
}

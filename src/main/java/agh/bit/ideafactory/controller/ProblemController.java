package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import agh.bit.ideafactory.helpers.FileUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProblemController {


	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"/problem", "/problem/list"}, method = RequestMethod.GET)
	public String listProblems(ModelMap model) {

		List<Problem> problems = problemService.getProblems();
		model.addAttribute("problemList", problems);
		return "problem/send";
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

    @RequestMapping(value = "problem/send", method = RequestMethod.POST)
    public String saveProblem(@ModelAttribute("uploadForm") FileUploadForm uploadForm,
                              String problemTitle,
                              Principal principal,
                              ModelMap model) throws IOException {
            List<MultipartFile> problemTestSet = uploadForm.getFiles();
        System.out.println(uploadForm.getFiles().size());
        if(problemTestSet != null)
        for(MultipartFile test : problemTestSet){
            System.out.println(test.getName());
        }
        else {
            System.out.println("asdsad");
            problemTestSet = new ArrayList<MultipartFile>();
            //problemSet.add();
        }
        //User user = userService.getUserByUserNameFetched(principal.getName());
        //problemService.saveProblemOnServer(problem, problemTestSet, user, problemTitle);

        return "problem/send";
    }
	
	
}

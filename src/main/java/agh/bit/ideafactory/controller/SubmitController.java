package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import agh.bit.ideafactory.helpers.UploadFile;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.SubmitService;
import agh.bit.ideafactory.service.UserService;

@Controller
public class SubmitController {

	@Autowired
	private SubmitService submitService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/submit/send" , method = RequestMethod.GET)
	public String sendSubmit(ModelMap model, HttpSession session) {
		
		System.out.println("asdboaibsdibasdpibaspbdaipsbdpasibdipasbd");
		model.addAttribute(new UploadFile());

		return "submit/send";
	}
	

	
	
	
	@RequestMapping(value="/submit/send" , method = RequestMethod.POST) // wersja ze zwykłym imputem
	public String create(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("problem_id") String problemId, Principal principal) {
		if ( !file.isEmpty()) {
			try {
				User user = userService.getUserByUserNameFetched(principal.getName());
				submitService.saveSubmitOnServer(file, user, Long.valueOf(problemId));
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/problem/list";
			}

			return "redirect:/submit/list";
		}
		else {
			// TODO add error msg to model
			return "redirect:/submit/send";
		}
	}

	
	
	
	@RequestMapping(value="/submit/list", method = RequestMethod.GET)
	public String listSubmits(ModelMap model) {
		
		return "submit/list";
	}
	
	
}

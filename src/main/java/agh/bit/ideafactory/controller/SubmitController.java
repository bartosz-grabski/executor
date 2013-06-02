package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.helpers.LanguageEnum;
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
	public String sendSubmit(@RequestParam(value="id", required=true) String problemId,ModelMap model, HttpSession session) {
		model.addAttribute(new UploadFile());
		
		List<LanguageEnum> languages = LanguageEnum.getAllLanguagesAsList();
		model.addAttribute("languages", languages);
		
		return "submit/send";
	}
	

	
	
	
	@RequestMapping(value="/submit/send" , method = RequestMethod.POST) 
	public String create(ModelMap model, @RequestParam("file") MultipartFile file, 
			@RequestParam(value="id", required=true) String problemId,
			@RequestParam(value="languageSelect", required=false) String language, Principal principal) {
		
		//System.err.println("Language = " +request.getParameter("languageSelect"));
		System.err.println("Language = " +language);
		LanguageEnum lang = null;
		if ( language != null) 
			lang = LanguageEnum.getLanguageByName(language);
		
		System.err.println("Language from enum = " +lang);
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

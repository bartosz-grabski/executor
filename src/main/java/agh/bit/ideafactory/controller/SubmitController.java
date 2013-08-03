package agh.bit.ideafactory.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.SubmitLanguageException;
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

	@RequestMapping(value = "/submit/send", method = RequestMethod.GET)
	public String sendSubmit(@RequestParam(value = "id", required = true) String problemId, ModelMap model, HttpSession session) {
		model.addAttribute(new UploadFile());

		addLanguagesToModelMap(model);

		return "submit/send";
	}

	@RequestMapping(value = "/submit/send", method = RequestMethod.POST)
	public String create(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam(value = "id", required = true) String exerciseId,
			@RequestParam(value = "languageSelect", required = false) String languageName, Principal principal, HttpServletRequest request) {

		LanguageEnum language = null;
		if (languageName != null)
			language = LanguageEnum.getLanguageByName(languageName);

		if (!file.isEmpty()) {
			try {
				User user = userService.getUserByUserNameFetched(principal.getName());
				submitService.saveSubmitOnServer(file, user, Long.valueOf(exerciseId), language);
			} catch (IOException e) {
				return "redirect:/problem/list";
			} catch (SubmitLanguageException e) {
				model.addAttribute("error", e.getMessage());
				addLanguagesToModelMap(model);
				return "submit/send";
			}

			return "redirect:/submit/list";
		} else {
			model.addAttribute("error", "Please choose a file to send!");
			addLanguagesToModelMap(model);
			return "submit/send";
		}
	}

	@RequestMapping(value = "/submit/list", method = RequestMethod.GET)
	public String listSubmits(ModelMap model) {

		return "submit/list";
	}

	private void addLanguagesToModelMap(ModelMap model) {
		List<LanguageEnum> languages = LanguageEnum.getAllLanguagesAsList();
		model.addAttribute("languages", languages);
	}

}

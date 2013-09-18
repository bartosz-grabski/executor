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
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.service.ProblemService;
import agh.bit.ideafactory.service.TestService;

@Controller
public class TestController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private TestService testService;

	@RequestMapping(value = "test/upload", method = RequestMethod.GET)
	public String uploadTest(@RequestParam(value = "id", required = true) long problemId, @RequestParam(value = "name") String problemName, ModelMap model) {
		return "test/upload";
	}

	@RequestMapping(value = "/test/upload", method = RequestMethod.POST)
	public String saveTest(@ModelAttribute("fileUploadForm") final FileUploadForm uploadForm, @RequestParam("id") Long problemID, ModelMap model, Principal principal) throws IOException {
		List<MultipartFile> problemTestSet = uploadForm.getFiles();
		problemService.addTestsToProblem(problemService.getById(problemID), problemTestSet);
		return "redirect:/problem/list";
	}

	@RequestMapping(value = "test/download", method = RequestMethod.GET)
	public String downloadTest(@RequestParam("id") Long testID, @RequestParam("type") String type, HttpServletResponse response) {

		Test test = testService.getTestByID(testID);
		byte[] content;
		content = (type.contains("input")) ? test.getTestInputFile() : test.getTestOutputFile();
		try {
			OutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			out.write(content);
			out.flush();
			out.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

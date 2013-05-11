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
	
//	@RequestMapping(value="/submit/send" , method = RequestMethod.POST)
//	public String create(UploadFile uploadFile, BindingResult result, HttpServletRequest request) {
//		if (result.hasErrors())	{
//			for(ObjectError error : result.getAllErrors()) {
//				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
//			}
//			return "submit/send";
//		}
//		File f = new File("C:\\Siatkówka\\"+uploadFile.getFileData().getOriginalFilename());
//		try {
//			//uploadFile.getFileData().getFileItem().write(f);
//			String fileName = request.getRealPath("") +
//	                   "\\submits\\"+ uploadFile.getFileData().getOriginalFilename();
//			System.out.println(fileName);
//			f = new File(fileName);
//			uploadFile.getFileData().getFileItem().write(f);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// Some type of file processing...
//	    System.err.println("-------------------------------------------");
//	    System.err.println("Test upload: " + uploadFile.getFileData().getOriginalFilename());
//	    System.err.println("-------------------------------------------");
//	    
//	    
//	    
//		return "redirect:/submit/list";
//	}
//	<form:form modelAttribute="uploadFile" method="post" enctype="multipart/form-data"> // zapisuje do web-appa - dotyczy tego co na górze
//    <fieldset>
//        <legend>Upload Fields</legend>
//        <p>
//            <form:label for="fileData" path="fileData">File</form:label><br/>
//            <form:input path="fileData" type="file"/>
//        </p>
//				
//
//        <p>
//            <input type="submit" />
//        </p>
//
//    </fieldset>
//</form:form>
	
	
//	@RequestMapping(value="/submit/send" , method = RequestMethod.POST)
//	public String create(Submit submit, BindingResult result) {
//		if (result.hasErrors())	{
//			for(ObjectError error : result.getAllErrors()) {
//				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
//			}
//			return "submit/send";
//		}
//		
//				    // Some type of file processing...
//		System.err.println("-------------------------------------------");
//		System.err.println("Test upload: " + submit.getId());
//		System.err.println("Test upload: " + submit.getFileData().getOriginalFilename());
//		System.err.println(submit.getFilePath());
//		System.err.println(submit.getFileData().getName());
//		System.err.println("-------------------------------------------");	 
//		
//		return "redirect:/submit/list";
//	}
	
	
	
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
//	<html>  ZE STRONY SPRINGA - ZAPISUJE DO FOLDERU PROJEKTU - dotyczy tego na górze
//    <head>
//        <title>Upload a file please</title>
//    </head>
//    <body>
//        <h1>Please upload a file</h1>
//        <form method="post" action="/form" enctype="multipart/form-data">
//            <input type="text" name="name"/>
//            <input type="file" name="file"/>
//            <input type="submit"/>
//        </form>
//    </body>
//</html>
	
	
	
	@RequestMapping(value="/submit/list", method = RequestMethod.GET)
	public String listSubmits(ModelMap model) {
		
		return "submit/list";
	}
	
	
}

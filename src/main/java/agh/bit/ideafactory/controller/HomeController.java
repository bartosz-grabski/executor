package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
    String active = "home";
	
	@RequestMapping(value={"/","/home"})
	public String welcome(ModelMap model){
		
		return "home/home";
	}

}

package agh.bit.ideafactory.controller;

import agh.bit.ideafactory.service.ExampleService;
import agh.bit.ideafactory.model.Example;
import agh.bit.ideafactory.serviceimpl.ExampleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


//import javax.servlet.http.HttpServletRequest;


@Controller
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    public Example example = new Example("imie");


    @RequestMapping(value="/example")
    public String welcomeScreen(ModelMap model){
         model.addAttribute("example", example);
         return "example";
    }

    public ExampleController(){
        super();
    }



}

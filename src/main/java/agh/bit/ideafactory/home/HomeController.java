package agh.bit.ideafactory.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class HomeController {
    private static final String HELLO_MESSAGE = "Maven + Spring MVC + Jetty. Hello";
    private static final String MESSAGE_ATTR = "message";

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        model.addAttribute(MESSAGE_ATTR, HELLO_MESSAGE + ".");

        return "home";
    }

    @RequestMapping(value = "/home/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute(MESSAGE_ATTR, HELLO_MESSAGE + " " + name + ".");

        return "home";
    }
}
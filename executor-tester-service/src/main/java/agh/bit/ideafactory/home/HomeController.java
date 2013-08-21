package agh.bit.ideafactory.home;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String HELLO_MESSAGE = "Maven + Spring MVC + Jetty. Hello";
    private static final String MESSAGE_ATTR = "message";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        model.addAttribute(MESSAGE_ATTR, HELLO_MESSAGE + ".");

        return "home";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute(MESSAGE_ATTR, HELLO_MESSAGE + " " + name + ".");

        return "home";
    }
}

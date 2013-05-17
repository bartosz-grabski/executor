package agh.bit.ideafactory.home;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class TemplateController {


    @RequestMapping(value="/template")
    public String welcome(ModelMap model, Principal principal) {
        return "template";
    }
}

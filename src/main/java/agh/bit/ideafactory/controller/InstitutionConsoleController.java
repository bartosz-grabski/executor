package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bgrabski
 */
@Controller
public class InstitutionConsoleController {

    @RequestMapping(value="/business/console", method = RequestMethod.GET)
    public @ResponseBody String console() {
        return "TODO";
    }
}

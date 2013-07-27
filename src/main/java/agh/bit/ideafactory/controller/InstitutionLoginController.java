package agh.bit.ideafactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static agh.bit.ideafactory.helpers.ModelMapUtils.setError;

/**
 * @author bgrabski
 */
@Controller
public class InstitutionLoginController {

    @RequestMapping(value="/business/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "home/institution_login";
    }

    @RequestMapping(value="/business/loginfailed", method = RequestMethod.GET)
    public String loginfailed(ModelMap model) {
        setError(model,"true");
        return "home/institution_login";
    }
}

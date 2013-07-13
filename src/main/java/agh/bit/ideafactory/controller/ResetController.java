package agh.bit.ideafactory.controller;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 13.07.13
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */

import agh.bit.ideafactory.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is responsible for handling reset password requests
 */
@Controller
public class ResetController {


    @Autowired
    private MailService mailService;
    /**
     * Method responsible for handling http get requests
     * @param model - model passed to the method
     * @return String representation of a view
     */
    @RequestMapping(value="/reset", method = RequestMethod.GET)
    public String resetPassword(ModelMap model) {

        return "home/reset";
    }

    /**
     * <pre>
     * Method responsible for handling http post requests
     *
     * If e-mail does not point to an existing account then model is set with error and message properties
     * </pre>
     * @param model - model passed to the method
     * @param request - http requests, containing mail to be sent to
     * @return String representation of a view
     */
    @RequestMapping(value="/reset", method = RequestMethod.POST)
    public String resetPassword(ModelMap model, HttpServletRequest request) {

        return "home/reset";
    }

}

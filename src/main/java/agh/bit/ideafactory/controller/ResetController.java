package agh.bit.ideafactory.controller;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 13.07.13
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */

import agh.bit.ideafactory.exception.NoSuchAttributeException;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.MailService;
import agh.bit.ideafactory.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import static agh.bit.ideafactory.helpers.ModelMapUtils.*;
/**
 * This class is responsible for handling reset password requests
 */
@Controller
public class ResetController {

    private static final int PASSWORD_LENGTH = 10;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
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

        String email = null;
        try {
            email = getEmailFromRequest(request);
        } catch (NoSuchAttributeException e) {
            setError(model,e.getMessage());
            return "home/reset";
        }

        User user = userService.getUserByEmail(email);
        if (user == null) {
            setError(model,"No such user");
            return "home/reset";
        }
        //Generating random password
        String newPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
        user.setPassword(passwordEncoder.encodePassword(newPassword, user.getUsername()));
        userService.update(user);

        mailService.sendMail("from@from.pl",email,"Password reset","New password \n" + newPassword);

        setSuccess(model,"Password reset and sent");
        return "home/reset";
    }

    /**
     * Returns email address String object
     * @param request
     * @return String email
     * @throws agh.bit.ideafactory.exception.NoSuchAttributeException
     */
    private String getEmailFromRequest(HttpServletRequest request) throws NoSuchAttributeException {
        String email = (String) request.getParameter("email");
        if (email == null) throw new NoSuchAttributeException("Email attribute not found!");
        return email;
    }

}

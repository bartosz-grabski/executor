package agh.bit.ideafactory.controller;

import static agh.bit.ideafactory.helpers.ModelMapUtils.*;

import agh.bit.ideafactory.helpers.TokenGenerator;
import agh.bit.ideafactory.model.Token;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.MailService;
import agh.bit.ideafactory.service.TokenService;
import agh.bit.ideafactory.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author bgrabski
 * Class for handling register requests and responses
 */
@Controller
public class RegisterController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserService userService;

    @Autowired
    public MailService mailService;

    @Autowired
    public TokenService tokenService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public TokenGenerator tokenGenerator;

    /**
     *
     * @param model Not used
     * @return String name of view
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String main(ModelMap model) {
        return "home/register";
    }

    /**
     * <pre>
     * Method responsible for processing HTTP POST data to create new account.
     * If account is not active it resends activation mail to the user.
     *
     * When information is not valid (mail structure, username size) then model is filled with
     * error message properties and returned to view
     * </pre>
     *
     * @param model ModelMap to be filled and returned to the view
     * @param request HTTP POST request
     * @return String representation of the view' name
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap model, HttpServletRequest request) {

        if(!confirmPassword(request.getParameter("password"),request.getParameter("passwordconf"))) {
            setError(model, "Passwords do not match!");
            return "home/register";
        };

        Token newToken;
        User newUser;

        try {
            StringBuffer sb = new StringBuffer();
            newUser = createUserFromRequest(request);
            Set<ConstraintViolation<User>> constraintViolations = validateUser(newUser);
            if (constraintViolations.size() != 0) {
                for (ConstraintViolation<User> u : constraintViolations) {
                    sb.append(u.getPropertyPath().toString() + ":" + u.getMessage());
                }
                setError(model, sb.toString());
                return "home/register";
            }
            newUser.setPassword(passwordEncoder.encodePassword(newUser.getPassword(), newUser.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
            setError(model, "Error validating user! Try again");
            return "home/register";
        }
        //finding if user already exists
        try {
            User result = userService.getUserByUserName(request.getParameter("username"));
            if (result != null && result.isEnabled()) {
                setError(model, "Account exists!");
                return "home/register";
            }

            newToken = tokenGenerator.generateToken();


            if (result != null && !result.isEnabled()) {
                setError(model, "Account not activated - resending activation mail!");
                logger.error("Account not activated - activation mail resent");
                newToken.setUser(result);
                tokenService.saveToken(newToken);
                sendMail(result, newToken.getToken(), request);
                return "home/register";
            }

        } catch (Exception e) {
            logger.error("Error in fetching user");
            setError(model, "Internal error! Please try again");
            return "home/register";
        }

        //user does not exist - add new one and send mail

        try {
            userService.addUser(newUser);
            newToken.setUser(newUser);
            tokenService.saveToken(newToken);
            sendMail(newUser, newToken.getToken(), request);
            setSuccess(model, "Message sent. Check your mail!");
        } catch (Exception e) {
            e.printStackTrace();
            setError(model, "Internal error! Please try again");
        }
        return "home/register";
    }
    /**
     * <pre>
     * Method used for accepting tokens from activation links.
     *
     * Activation links should be formed like /register/{token}
     *
     * Those were sent to user in registration process
     * </pre>
     * @param token String token passed in link
     * @param model ModelMap returned to the view
     * @return String representation of view
     */
    @RequestMapping("/register/{token}")
    public String activate(@PathVariable("token") String token, ModelMap model) {
        try {
            Token result = tokenService.findToken(token);
            if (result == null) {
                setError(model, "Token deprecated");
                return "home/register";
            }
            User userToUpdate = result.getUser();
            userToUpdate.setEnabled(true);
            userService.update(userToUpdate);
            tokenService.deleteToken(result);
            setSuccess(model, "Account successfully activated! Enjoy!");

        } catch (Exception e) {
            setError(model, "Internal error! Please try again");
            e.printStackTrace();
        }
        return "home/register";
    }

    //-------UTILS--------
    private void sendMail(User u, String message, HttpServletRequest request) {

        mailService.sendMail("from@from.pl", u.getEmail(),
                "AGH Online Judge Registration", request.getRequestURL().append("/").append(message).toString());

    }

    /**
     * <pre>
     * Method for creating new User object from HTTP request
     * </pre>
     *
     * @param request HTTP Request with user data to be process
     * @return Newly created User object
     */
    private User createUserFromRequest(HttpServletRequest request) {
        User newUser = new User();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setEnabled(false);
        return newUser;
    }

    /**
     * Method for validating constraints
     * @param u User object to be validated
     * @return Set of ConstraintViolations on User object
     */
    private Set<ConstraintViolation<User>> validateUser(User u) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(u);
    }

    /**
     * Encodes password. Uses username as salt. Sets encoded password for passed User object
     * @param u User object
     */
    private void hashPassword(User u) {
        String hashed = passwordEncoder.encodePassword(u.getPassword(), u.getUsername());
        u.setPassword(hashed);
    }


    //TODO - need more abstraction over getUserByUserName to determine if user exists
    /**
     * <pre>
     *     Checks whether user exists in database
     * </pre>
     * @param u User object
     * @return True if user exists, false otherwise
     */
    private boolean userExists(User u) {

        return false;
    }

    /**
     * <pre>
     *     Method for confirming whether password and password confirm are equal
     * </pre>
     * @param password String password
     * @param confirm String password confirm
     * @return True if equal, false otherwise
     */
    private boolean confirmPassword(String password, String confirm) {
        return password != null ? password.equals(confirm) : false;
    }


}

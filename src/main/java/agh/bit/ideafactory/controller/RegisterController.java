package agh.bit.ideafactory.controller;

import agh.bit.ideafactory.helpers.TokenGenerator;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.model.Token;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.AuthorityService;
import agh.bit.ideafactory.service.MailService;
import agh.bit.ideafactory.service.TokenService;
import agh.bit.ideafactory.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Controller 
public class RegisterController {

    Logger logger = Logger.getLogger(RegisterController.class);

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
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String main(ModelMap model) {
		return "home/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ModelMap model, HttpServletRequest request) {

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
            newUser.setPassword(passwordEncoder.encodePassword(newUser.getPassword(),newUser.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
            setError(model, "Error validating user! Try again");
            return "home/register";
        }
        //finding if user already exists
        try {

            User result = userService.getUserByUserName(request.getParameter("username"));
            if (result != null && result.isEnabled()) {
                setError(model,"Account exists!");
                return "home/register";
            }

            newToken = tokenGenerator.generateToken();


            if (result != null && !result.isEnabled()) {
                    setError(model,"Account not activated - resending activation mail!");
                    newToken.setUser(result);
                    tokenService.saveToken(newToken);
                    sendMail(result, newToken.getToken(), request);
                    return "home/register";
            }

        } catch (Exception e) {
                logger.error("Error in fetching user");
                setError(model,"Internal error! Please try again");
                return "home/register";
        }

        //user does not exist - add new one and send mail

        try {
            userService.addUser(newUser);
            newToken.setUser(newUser);
            tokenService.saveToken(newToken);
            sendMail(newUser, newToken.getToken(),request);
            setSuccess(model,"Message sent. Check your mail!");
        } catch (Exception e) {
            e.printStackTrace();
            setError(model,"Internal error! Please try again");
        }
		return "home/register";
	}

    @RequestMapping("/register/{token}")
    public String activate(@PathVariable("token") String token, ModelMap model) {
        try {
            Token result = tokenService.findToken(token);
            if (result == null) {
                setError(model,"Token deprecated");
                return "home/register";
            }
            User userToUpdate = result.getUser();
            userToUpdate.setEnabled(true);
            userService.update(userToUpdate);
            tokenService.deleteToken(result);
            setSuccess(model,"Account successfully activated! Enjoy!");

        } catch (Exception e) {
            setError(model,"Internal error! Please try again");
            e.printStackTrace();
        }
        return "home/register";
    }
    //-------UTILS--------
    private void sendMail(User u, String message, HttpServletRequest request) {

        mailService.sendMail("from@from.pl",u.getEmail(),
                "AGH Online Judge Registration",request.getRequestURL().append("/").append(message).toString());

    }

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

    private Set<ConstraintViolation<User>> validateUser(User u) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator.validate(u);
    }

    private void hashPassword(User u) {
        String hashed = passwordEncoder.encodePassword(u.getPassword(),u.getUsername());
        u.setPassword(hashed);
    }

    private void setError(ModelMap model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("error", true);
    }

    private void setSuccess(ModelMap model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("success", true);
    }

    private boolean userExists(User u) {

        return false;
    }

	

}

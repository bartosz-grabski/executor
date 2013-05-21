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
import java.util.ArrayList;
import java.util.HashSet;


@Controller 
public class RegisterController {

    Logger logger = Logger.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenGenerator tokenGenerator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String main(ModelMap model) {
		return "home/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(ModelMap model, HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String hashed = passwordEncoder.encodePassword(password,username);
		User user = new User();
        user.setUsername(username);
        user.setPassword(hashed);
        user.setEmail(email);
        user.setEnabled(false);

        String token;
        Token newToken;

        //finding if user already exists
        try {

            User result = userService.getUserByUserName(username);
            if (result != null && result.isEnabled()) {
                model.addAttribute("message", "Account already exists!");
                model.addAttribute("error",true);
                return "/home/register";
            }

            token = tokenGenerator.generateToken();
            newToken = new Token();
            newToken.setToken(token);


            if (result != null && !result.isEnabled()) {
                try {
                    model.addAttribute("message", "Account not activated - resending activation mail!");
                    model.addAttribute("error",true);
                    tokenService.saveToken(newToken);
                    sendMail(result, token,request);
                    return "/home/register";
                } catch (Exception e) {

                }
            }

        } catch (Exception e) {
                logger.error("Error in fetching user");
                model.addAttribute("error",true);
                model.addAttribute("message", "Internal error - please try again!");
                return "/home/register";
        }

        //user does not exist - add new one and send mail

        try {
            userService.addUser(user);
            newToken.setUser(user);
            tokenService.saveToken(newToken);
            sendMail(user,token,request);
            model.addAttribute("success", true);
            model.addAttribute("message", "Message sent - check your mail!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", "Internal error - please try again!");
        }
		return "home/register";
	}

    @RequestMapping("/register/{token}")
    public String activate(@PathVariable("token") String token, ModelMap model) {
        try {
            Token result = tokenService.findToken(token);
            if (result == null) {
                model.addAttribute("message", "Token deprecated");
                model.addAttribute("error", true);
                return "/home/register";
            }
            User userToUpdate = result.getUser();
            userToUpdate.setEnabled(true);
            userService.update(userToUpdate);
            tokenService.deleteToken(result);
            model.addAttribute("message", "Activation successful!");
            model.addAttribute("success", true);

        } catch (Exception e) {
            model.addAttribute("message", "Internal error! Please try again");
            model.addAttribute("error", true);
            e.printStackTrace();
        }
        return "/home/register";
    }

    private void sendMail(User u, String message, HttpServletRequest request) {

        mailService.sendMail("from@from.pl",u.getEmail(),
                "AGH Online Judge Registration",request.getRequestURL().append("/").append(message).toString());

    }

	

}

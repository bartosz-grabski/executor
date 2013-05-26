package agh.bit.ideafactory.test.controller;

import agh.bit.ideafactory.controller.RegisterController;
import agh.bit.ideafactory.helpers.TokenGenerator;
import agh.bit.ideafactory.model.Token;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.MailService;
import agh.bit.ideafactory.service.TokenService;
import agh.bit.ideafactory.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 23.05.13
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @Mock private UserService userService;
    @Mock private TokenService tokenService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MailService mailService;
    @Mock private TokenGenerator tokenGenerator;

    @InjectMocks
    private RegisterController controller = new RegisterController();

    private ModelMap model;
    private HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        model = new ModelMap();
    }

    @Test
    public void testProperNonExistingUser() {
        String username = "properUser";
        String password = "properPass";
        String email = "properEmail@gmail.com";
        request = createRequest(username,password,email);

        Token token = mock(Token.class);

        when(userService.getUserByUserName(username)).thenReturn(null);
        when(passwordEncoder.encodePassword(password,username)).thenReturn("encoded");
        when(tokenGenerator.generateToken()).thenReturn(token);

        String returnView = controller.register(model,request);
        verify(userService).addUser(any(User.class));
        verify(mailService).sendMail(anyString(),eq(email),anyString(),anyString());
        verify(tokenService).saveToken(token);

        assertEquals(returnView,"home/register");
        assertTrue(model.containsAttribute("success"));
        assertTrue(model.containsAttribute("message"));
    }

    @Test
    public void testProperExistingAndEnabledUser() {
        String username = "existingUser";
        String password = "existingUserPass";
        String email = "existingMail@gmail.com";
        request = createRequest(username,password,email);

        User existingUser = mock(User.class);

        when(userService.getUserByUserName(username)).thenReturn(existingUser);
        when(existingUser.getEnabled()).thenReturn(true);

        String returnView = controller.register(model,request);

        assertEquals(returnView,"home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));


    }

    @Test
    public void testProperExistingAndDisabledUser() {

        String username = "existingUser";
        String password = "existingUserPass";
        String email = "existingMail@gmail.com";
        request = createRequest(username,password,email);

        User existingUser = mock(User.class);
        Token token = mock(Token.class);

        when(userService.getUserByUserName(username)).thenReturn(existingUser);
        when(existingUser.getEnabled()).thenReturn(false);
        when(existingUser.getEmail()).thenReturn("some@mail.com");
        when(tokenGenerator.generateToken()).thenReturn(token);

        String returnView = controller.register(model,request);

        verify(mailService).sendMail(anyString(),eq("some@mail.com"),anyString(),anyString());
        verify(tokenService).saveToken(token);
        verify(token).setUser(existingUser);
        assertEquals(returnView,"home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));



    }

    @Test
    public void testIllegalUserName() {
        String username, password, email, returnView;
        password = "SomeProperPass";
        email = "Some@Proper.mail";

        //Null username
        username = null;
        request = createRequest(username,password,email);

        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Empty username
        username = "";
        request = createRequest(username,password,email);

        model.clear();
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Too short username
        username = "aaa";
        request = createRequest(username,password,email);

        model.clear();
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Too long username
        username = "SomeRidiculouslyLongUsernameThatHasAbsolutelyNoSense";
        model.clear();
        request = createRequest(username,password,email);
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

    }

    @Test
    public void testIllegalPassword() {
        String username, password, email, returnView;
        username = "SomeProperUsername";
        email = "Some@Proper.mail";

        //Null password
        password = null;
        request = createRequest(username,password,email);

        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Empty password
        password = "";
        request = createRequest(username,password,email);

        model.clear();
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Too short password
        password = "aaa";
        request = createRequest(username,password,email);

        model.clear();
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Too long password
        password = "SomeRidiculouslyLongUsernameThatHasAbsolutelyNoSense";
        model.clear();
        request = createRequest(username,password,email);
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

    }

    @Test
    public void testIllegalMail() {
        String username, password, email, returnView;
        username = "SomeProperUsername";
        password = "SomeProperPassword";

        //Null email
        email = null;
        request = createRequest(username,password,email);

        model.clear();
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));

        //Improper mail
        email = "someImproper.com";
        request = createRequest(username,password,email);

        model.clear();
        returnView = controller.register(model,request);
        assertEquals(returnView, "home/register");
        assertTrue(model.containsAttribute("error"));
        assertTrue(model.containsAttribute("message"));


    }



    private HttpServletRequest createRequest(String username, String password, String email) {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addParameter("username",username);
        req.addParameter("password",password);
        req.addParameter("email", email);
        return req;
    }


}

package agh.bit.ideafactory.test.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import agh.bit.ideafactory.controller.HomeController;



/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 22.05.13
 * Time: 09:07
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    HomeController controller = new HomeController();
    @Before
    public void setUp() {
        controller = new HomeController();
    }

    @Mock
    ModelMap model;


    @Test
    public void testHomePage() throws Exception {
        String view = controller.welcome(model);
        assertTrue(view == "home/home");
    }

}

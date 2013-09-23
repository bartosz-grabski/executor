package agh.bit.ideafactory.rmi.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import agh.bit.ideafactory.rmi.controller.ResponseController;
import agh.bit.ideafactory.rmi.model.TesterOutput;
import agh.bit.ideafactory.rmi.services.ResultService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mvc-dispatcher-servlet.xml" })
public class ResponseControllerTest {

	@Mock
	private ResultService resultService;

	@InjectMocks
	private ResponseController controller;

	private MockMvc mockMvc;
	private String json = "{\"submit_id\": \"13579\",\"result_code\": \"TLE\",\"test_results\": [{\"id\": 1, \"result_code\": \"OK\", \"memory\": 1000, \"time\" : 1000},{\"id\": 2, \"result_code\": \"TLE\", \"memory\": 2000, \"time\" : 2001},{\"id\": 80085, \"result_code\": \"NT\", \"memory\": 0, \"time\": 0} ]}";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(controller).build();
	}

	@Test
	public void shouldAcceptJSONOutputAndDelegateToResultService() throws Exception {

		mockMvc.perform(post("/output").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
		verify(resultService).putResult(any(TesterOutput.class));
	}

}

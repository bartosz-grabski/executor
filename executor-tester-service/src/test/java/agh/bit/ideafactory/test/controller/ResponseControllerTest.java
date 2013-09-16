package agh.bit.ideafactory.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import agh.bit.ideafactory.rmi.controller.ResponseController;
import agh.bit.ideafactory.rmi.model.TesterOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mvc-dispatcher-servlet.xml", "classpath:jdbc-context.xml" })
public class ResponseControllerTest {

	private static final String JSON_FILE = "json-test-objects.json";
	
	ResponseController controller;

	MockMvc mockMvc;

	String submitId = "12345";

	static List<TesterOutput> outputs;

	@BeforeClass
	public static void loadJson() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		outputs = mapper.readValue(jsonFile(), new TypeReference<List<TesterOutput>>() {
		});
	}

	@Before
	public void setUp() {
		controller = new ResponseController();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void shouldPassWithValidJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		for (TesterOutput output : outputs) {
			String json = mapper.writeValueAsString(output);
			System.out.println("dupa" + json);
			mockMvc.perform(post("/" + submitId).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		}

	}

	@Test
	public void shouldReturnBadRequestWithInvalidJson() throws Exception {
		mockMvc.perform(post("/" + submitId).content("someInvalidContent").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	public void shouldReturnBadRequestWithInvalidContentType() throws Exception {
		mockMvc.perform(post("/" + submitId).content("someInvalidContent").contentType(MediaType.APPLICATION_XML)).andExpect(status().isUnsupportedMediaType());

		mockMvc.perform(post("/" + submitId).content("someInvalidContent").contentType(MediaType.TEXT_HTML)).andExpect(status().isUnsupportedMediaType());

		mockMvc.perform(post("/" + submitId).content("someInvalidContent").contentType(MediaType.TEXT_PLAIN)).andExpect(status().isUnsupportedMediaType());

		mockMvc.perform(post("/" + submitId).content("someInvalidContent").contentType(MediaType.IMAGE_GIF)).andExpect(status().isUnsupportedMediaType());

		mockMvc.perform(post("/" + submitId).content("someInvalidContent").contentType(MediaType.IMAGE_JPEG)).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void shouldReturnMethodNotAllowedWithInvalidHTTPMethod() throws Exception {
		mockMvc.perform(get("/" + submitId)).andExpect(status().isMethodNotAllowed());
	}

	private static InputStream jsonFile() {
		return ResponseControllerTest.class.getClassLoader().getResourceAsStream(JSON_FILE);
	}

}

package agh.bit.ideafactory.rmi.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import agh.bit.ideafactory.model.helpers.LanguageEnum;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.utils.JSONConverter;

public class JSONConverterTest {

	private Submit submit;
	private List<agh.bit.ideafactory.model.Test> tests;
	private JSONConverter jsonConverter = new JSONConverter();
	
	@Test
	public void shouldProduceValidJSONInfoString() {
		String expectedOutput = "{\"id\":1,\"language\":\"C\",\"tests\":[{\"id\":1,\"memory\":10000,\"time\":480},{\"id\":2,\"memory\":10000,\"time\":480}]}";
		givenSubmit(1L, LanguageEnum.C);
		givenTests(new Long[] { 1L, 2L });
		String jsonOutput = jsonConverter.convertToInfoJSONString(submit, tests);
		assertEquals(expectedOutput, jsonOutput);
	}

	private void givenSubmit(Long id, LanguageEnum language) {
		Submit submit = new Submit();
		submit.setId(id);
		submit.setLanguageEnum(language);
		this.submit = submit;
	}

	private void givenTests(Long[] ids) {
		List<agh.bit.ideafactory.model.Test> tests = new ArrayList<agh.bit.ideafactory.model.Test>();
		for (Long id : ids) {
			agh.bit.ideafactory.model.Test test = new agh.bit.ideafactory.model.Test();
			test.setId(id);
			tests.add(test);
		}
		this.tests = tests;
	}

}

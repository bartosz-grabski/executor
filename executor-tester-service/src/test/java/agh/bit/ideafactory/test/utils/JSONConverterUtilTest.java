package agh.bit.ideafactory.test.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import agh.bit.ideafactory.rmi.model.Submit;
import agh.bit.ideafactory.utils.JSONConverter;

public class JSONConverterUtilTest {
	
	private Submit submit;
	private List<agh.bit.ideafactory.rmi.model.Test> tests;
	
	
	@Test
	public void shouldConvertToValidJSONInfoOutput() {
		givenSubmit(1L, "C", new byte[10]);
		givenTests(Arrays.asList(1L,2L));
		System.out.println(JSONConverter.convertToInfoJSONString(submit, tests));
	}
	
	private void givenSubmit(long submitId, String language, byte[] content) {
		Submit submit = new Submit();
		submit.setSubmitId(submitId);
		submit.setLanguage(language);
		submit.setContent(content);
		this.submit = submit;
	}
	
	private void givenTests(List<Long> ids) {
		List<agh.bit.ideafactory.rmi.model.Test> testList = new ArrayList<>();
		for (Long id : ids) {
			agh.bit.ideafactory.rmi.model.Test newTest = new agh.bit.ideafactory.rmi.model.Test();
			testList.add(newTest);
		}
		this.tests = testList;
	}

}

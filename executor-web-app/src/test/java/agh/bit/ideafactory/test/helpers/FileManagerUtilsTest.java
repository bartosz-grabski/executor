package agh.bit.ideafactory.test.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.helpers.FileManagerUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileManagerUtilsTest {

	private FileManagerUtils fileManagerUtils = new FileManagerUtils();

	@Test(expected = SubmitLanguageException.class)
	public void shouldThrowSubmitLanguageExceptionWhenNoExtension() throws SubmitLanguageException {

		String filePath = "NoDotInHere";

		fileManagerUtils.extractExtensionFromFilepath(filePath);

	}

	@Test(expected = SubmitLanguageException.class)
	public void shouldThrowSubmitLanguageExceptionWhenExtensionNotProper() throws SubmitLanguageException {

		String filePath = "noSuch.programminglanguage";

		fileManagerUtils.extractExtensionFromFilepath(filePath);
	}

	@Test(expected = SubmitLanguageException.class)
	public void shouldThrowSubmitLanguageExceptionWhenEmptyExtension() throws SubmitLanguageException {

		String filePath = "noSuch.";

		fileManagerUtils.extractExtensionFromFilepath(filePath);
	}

}

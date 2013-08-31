package agh.bit.ideafactory.helpers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.model.User;

@Component
public class FileManagerUtils {

	private static String SEPARATOR = System.getProperty("file.separator");

	public String getParentPathForSubmit(User user) throws IOException {

		File submitFile = new File("p");
		String parentPath = submitFile.getCanonicalPath().substring(0, submitFile.getCanonicalPath().lastIndexOf(SEPARATOR));
		parentPath = parentPath + SEPARATOR + "Uploads" + SEPARATOR + user.getUsername() + SEPARATOR + "submits" + SEPARATOR;
		return parentPath;
	}

	public String getParentPathForProblem(String problemName) throws IOException {
		File problemFile = new File("p");
		String parentPath = problemFile.getCanonicalPath().substring(0, problemFile.getCanonicalPath().lastIndexOf(SEPARATOR));
		parentPath = parentPath + SEPARATOR + "Uploads" + SEPARATOR + "Problems" + SEPARATOR + problemName + SEPARATOR;
		return parentPath;
	}

	public String getParentPathForTest(TestType testType, String problemName) throws IOException {
		File testFile = new File("p");
		String parentPath = testFile.getCanonicalPath().substring(0, testFile.getCanonicalPath().lastIndexOf(SEPARATOR));
		parentPath = parentPath + SEPARATOR + "Uploads" + SEPARATOR + "Problems" + SEPARATOR + problemName + SEPARATOR + "tests" + SEPARATOR;
		return parentPath;

	}

	public String getExtensionForSubmission(String path, LanguageEnum language) throws SubmitLanguageException {
		return language != null ? language.getExtension() : extractExtensionFromFilepath(path);
	}

	public String extractExtensionFromFilepath(String path) throws SubmitLanguageException {
		int indexOfLastDot = path.lastIndexOf(".");
		if (indexOfLastDot == -1) {
			throw new SubmitLanguageException("Extension of send file doesn't exists. Please change file name of choose extension from available list");
		}
		String extension = path.substring(indexOfLastDot);
		if ("".equals(extension) || !LanguageEnum.extensionExists(extension.substring(1))) {
			throw new SubmitLanguageException("Extension of send file doesn't match any programming language available");
		}

		return extension;
	}
}

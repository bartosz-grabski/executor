package agh.bit.ideafactory.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.model.User;


@Component
public class FileManager {

	public String saveSubmitFile(MultipartFile submittedFile, User user) throws IOException {
		String targetDirectory = getParentPath(user);
		String targetFilename = getTargetFilename(user);
		saveFile(submittedFile, targetDirectory, targetFilename);
		return targetDirectory+targetFilename;
	}

	
	private void saveFile(MultipartFile file, String targetDirectory, String targetFilename)
			throws FileNotFoundException, IOException {

		createDirectoryStructureIfNotExists(targetDirectory);
		FileOutputStream f = new FileOutputStream(targetDirectory + targetFilename);
		byte[] bytes = file.getBytes();
		f.write(bytes);
		f.close();
	}

	private void createDirectoryStructureIfNotExists(String parentPath) {
		File parentFile = new File(parentPath);
		if (  !parentFile.exists() && !parentFile.mkdirs()) {
			System.err.println("Error");
			throw new IllegalStateException("Couldn't create dir: " + parentFile);
		}
	}

	private String getParentPath(User user)
			throws IOException {
		
		String separator =  System.getProperty("file.separator");
		File submitFile = new File("p"); 
		String parentPath = submitFile.getCanonicalPath().substring(0, submitFile.getCanonicalPath().lastIndexOf(separator));
		parentPath = parentPath+separator+"Uploads"+separator+user.getUsername()+separator+"submits"+separator;
		return parentPath;
	}

	private String getTargetFilename(User user) {
		return "submit_"+getNextSubmitNumberFor(user);
	}

	private int getNextSubmitNumberFor(User user) {
		return user.getSubmits().size();
	}
	
}

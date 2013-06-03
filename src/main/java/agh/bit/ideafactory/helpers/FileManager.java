package agh.bit.ideafactory.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.model.User;


@Component
public class FileManager {

	
	@Autowired
	private FileManagerUtils fileManagerUtils;
	
	public String saveSubmitFile(MultipartFile submittedFile, User user, LanguageEnum language) throws IOException, SubmitLanguageException {
		String targetDirectory = fileManagerUtils.getParentPathForSubmit(user);
		String targetFilename = getTargetFilename(submittedFile,user,language);
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



	private String getTargetFilename(MultipartFile submittedFile ,User user,LanguageEnum language) throws SubmitLanguageException {
		return "submit_"+getNextSubmitNumberFor(user) + fileManagerUtils.getExtensionForSubmission(submittedFile.getOriginalFilename(), language); 
//		String result = "submit_"+getNextSubmitNumberFor(user);
//		String extension ;
//		if ( language == null){
//			
//			int indexOfLastDot = submittedFile.getOriginalFilename().lastIndexOf(".");
//			if ( indexOfLastDot == -1)
//				throw new SubmitLanguageException("Extension of send file doesn't exists. Please change file name of choose extension from available list");
//			extension = submittedFile.getOriginalFilename().substring(indexOfLastDot);
//			if ( extension.equals("") || LanguageEnum.checkIfExtensionExists(extension.substring(1)) == false)
//				throw new SubmitLanguageException("Extension of send file doesn't match any programming language available");
//			result = result+extension;
//
//			return result;
//		}
//		else {
//			result = result+"."+language.getExtension();
//			
//			return result;
//		}
	}

	private int getNextSubmitNumberFor(User user) {
		return user.getSubmits().size();
	}
	

	
	
	
	
	////////////////// private methods ////////////////////////

}

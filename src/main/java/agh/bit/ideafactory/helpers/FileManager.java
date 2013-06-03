package agh.bit.ideafactory.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.model.User;


@Component
public class FileManager {

	public String saveSubmitFile(MultipartFile submittedFile, User user, LanguageEnum language) throws IOException, SubmitLanguageException {
		String targetDirectory = getParentPath(user);
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

	private String getParentPath(User user)
			throws IOException {
		
		String separator =  System.getProperty("file.separator");
		File submitFile = new File("p"); 
		String parentPath = submitFile.getCanonicalPath().substring(0, submitFile.getCanonicalPath().lastIndexOf(separator));
		parentPath = parentPath+separator+"Uploads"+separator+user.getUsername()+separator+"submits"+separator;
		return parentPath;
	}

	private String getTargetFilename(MultipartFile submittedFile ,User user,LanguageEnum language) throws SubmitLanguageException {

		String result = "submit_"+getNextSubmitNumberFor(user);
		String extension ;
		if ( language == null){
			try {
				extension = submittedFile.getOriginalFilename().substring(submittedFile.getOriginalFilename().lastIndexOf("."));
				String extensionToCompare = extension.substring(1);
				if (LanguageEnum.checkIfExtensionExists(extensionToCompare) == false) {
					throw new SubmitLanguageException("Extension of send file doesn't match any programming language available");
				}			
				result = result+extension;
			}
			catch ( IndexOutOfBoundsException e) {
				throw new SubmitLanguageException("Extension of send file doesn't match any programming language available or does not exists");
			}
			return result;
		}
		else {
			result = result+"."+language.getExtension();
			
			return result;
		}
	}

	private int getNextSubmitNumberFor(User user) {
		return user.getSubmits().size();
	}
	
}

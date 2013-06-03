package agh.bit.ideafactory.helpers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.model.User;

@Component
public class FileManagerUtils {


	public String getParentPathForSubmit(User user)	throws IOException {
		
		String separator =  System.getProperty("file.separator");
		File submitFile = new File("p"); 
		String parentPath = submitFile.getCanonicalPath().substring(0, submitFile.getCanonicalPath().lastIndexOf(separator));
		parentPath = parentPath+separator+"Uploads"+separator+user.getUsername()+separator+"submits"+separator;
		return parentPath;
	}
	
	public String getExtensionForSubmission(String path, LanguageEnum language) throws SubmitLanguageException{ 
		   return language != null 
		       ? language.getExtension() 
		       : extractExtensionFromFilepath(path); 
		} 
	
	public String extractExtensionFromFilepath(String path) throws SubmitLanguageException{ 
		   int indexOfLastDot = path.lastIndexOf("."); 
		   if ( indexOfLastDot == -1) { 
		       throw new SubmitLanguageException("Extension of send file doesn't exists. Please change file name of choose extension from available list"); 
		   } 
		   String extension = path.substring(indexOfLastDot); 
		   if ( extension.equals("") || LanguageEnum.checkIfExtensionExists(extension.substring(1)) == false) { 
		       throw new SubmitLanguageException("Extension of send file doesn't match any programming language available"); 
		   } 
		   
		   return extension; 
		} 
		

	
	
}

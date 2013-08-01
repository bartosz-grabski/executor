package agh.bit.ideafactory.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import agh.bit.ideafactory.dao.ProblemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.model.User;


@Component
public class FileManager {

	
	@Autowired
	private FileManagerUtils fileManagerUtils;
	
	@Autowired
	private SubmitDao submitDao;

    @Autowired
    private ProblemDao problemDao;

    public String saveProblemFile(MultipartFile problemFile, User user, String problemName) throws IOException {
        String targetDirectory = fileManagerUtils.getParentPathForProblem(problemName);
        String targetFileName = getProblemFilename(problemFile);
        saveFile(problemFile,targetDirectory, targetFileName);

        return targetDirectory+targetFileName;
    }
	
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
	}

    private String getProblemFilename(MultipartFile problemFile){
        return "problem_"+getNextProblemNumber() + "_" + problemFile.getName();
    }

	private Long getNextSubmitNumberFor(User user) {
		return submitDao.getHighestIdOfUserSubmits(user)+1;
	}

    private Long getNextProblemNumber(){
        return problemDao.getHighestProblemID() + 1;
    }
	

}

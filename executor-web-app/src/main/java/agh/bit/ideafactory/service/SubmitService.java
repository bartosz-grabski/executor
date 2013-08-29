package agh.bit.ideafactory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.helpers.LanguageEnum;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;

public interface SubmitService {

	public List<Submit> getSubmitsByUser(User user);

	List<Submit> getSubmitsByProblem(Problem problem);

	void saveSubmitOnServer(MultipartFile file, User user, Long problemId, LanguageEnum language) throws IOException, SubmitLanguageException;

}
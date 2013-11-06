package agh.bit.ideafactory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;

public interface SubmitService {

	public List<Submit> getSubmitsByUser(User user);

	Submit saveSubmitOnServer(MultipartFile file, User user, Long problemId, String languageName) throws IOException, SubmitLanguageException;

	Submit findById(Long submitId);

}

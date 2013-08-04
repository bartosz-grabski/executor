package agh.bit.ideafactory.service;

import java.io.IOException;
import java.util.List;

import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface ProblemService {

	public List<Problem> getProblems();
	
	public Problem getById(Long id);

    public void addProblem(Problem problem);

    public void saveProblemOnServer(MultipartFile problemFile, List<MultipartFile> problemTestSet, User user, String title) throws IOException;
}

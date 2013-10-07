package agh.bit.ideafactory.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.FileExtensionException;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

public interface ProblemService {

	public List<Problem> getProblems();

	public Problem getById(Long id);

	public void addProblem(Problem problem);

	public void saveProblemOnServer(MultipartFile problemFile, List<MultipartFile> problemTestSet, User user, String title) throws IOException, FileExtensionException;

	public void addTestsToProblem(Problem problem, List<MultipartFile> problemTestSet) throws IOException;
}

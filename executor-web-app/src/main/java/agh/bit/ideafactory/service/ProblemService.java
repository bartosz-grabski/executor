package agh.bit.ideafactory.service;

import java.io.IOException;
import java.util.List;

import agh.bit.ideafactory.form.ProblemForm;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.exception.FileExtensionException;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.User;

public interface ProblemService {

	public List<Problem> getProblems();

	public List<Problem> getProblems(boolean active);

	public Problem getById(Long id);

	public void addProblem(Problem problem);

	public void saveProblemOnServer(ProblemForm problemForm, User user) throws IOException, FileExtensionException;

	public void addTestsToProblem(Long problemID, List<MultipartFile> problemTestSet) throws IOException, FileExtensionException;

	public void deleteProblem(Problem problem);

	public void deleteProblem(Problem problem, boolean keepHistory);

	public void updateProblem(Problem update);

	public List<Problem> findAllByUserName(String name);
}

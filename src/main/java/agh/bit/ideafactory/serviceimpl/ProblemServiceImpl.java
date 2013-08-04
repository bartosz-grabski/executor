package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.helpers.TestType;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Test;
import agh.bit.ideafactory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ProblemService;
import org.springframework.web.multipart.MultipartFile;


@Service("problemService")
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemDao problemDao;

    @Autowired
    private FileManager fileManager;

	@Override
	@Transactional
	public List<Problem> getProblems() {
		
		return problemDao.findAll();
	}

	@Override
	@Transactional
	public Problem getById(Long id) {
		return problemDao.findById(id);
	}


    @Override
    @Transactional
    public void addProblem(Problem problem){
        problemDao.save(problem);
    }

    @Override
    @Transactional
    public void saveProblemOnServer(MultipartFile problemFile, List<MultipartFile> problemTestSet, User user, String title) throws IOException {
        String problemFilePath = fileManager.saveProblemFile(problemFile, user, title);

        for(int i=0; i<problemTestSet.size(); i++){
            //String inputTestFilePath = fileManager.saveTestFile(problemTestSet.get(i), TestType.INPUT);
            //String outputTestFilePath = fileManager.saveTestFile(problemTestSet.get(i+1), TestType.OUTPUT);
            //Test test = prepareTest(inputTestFilePath, outputTestFilePath);
        }

        Problem problem = prepareProblem(title, problemFilePath, user);
        problemDao.save(problem);
    }

    private Problem prepareProblem(String title, String problemFilePath,  User user) throws IOException {
        Problem  problem = new Problem();
        problem.setUser(user);
        //problem.setFilePath();
        problem.setName(title);
        problem.setExercises(new ArrayList<Exercise>());


        // / /problem.setTests();
        return problem;
    }

    private List<Test> prepareTests(List<MultipartFile> testFiles){
        List<Test> tests = new ArrayList<Test>();
        for(MultipartFile testFile:testFiles){

        }
        return null;
    }
}

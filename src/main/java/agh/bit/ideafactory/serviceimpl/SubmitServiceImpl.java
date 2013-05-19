package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.model.ResultStatusEnum;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.SubmitService;

@Service
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private FileManager fileManager;
	
	@Autowired
	public SubmitDao submitDao;
	
	@Autowired
	public ResultDao resultDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProblemDao problemDao;

	@Override
	public List<Submit> getSubmitsByUser(User user) {
		return submitDao.getSubmitsByUser(user);
	}

	@Override
	public List<Submit> getSubmitsByProblem(Problem problem) {
		return submitDao.getSubmitsByProblem(problem);
	}


	@Override
        @Transactional
	public void saveSubmitOnServer(MultipartFile submittedFile, User user, Long problemId ) throws IOException {

		String submitFileName = fileManager.saveSubmitFile(submittedFile, user);
		
		Submit submit = prepareSubmit(user, submitFileName, problemId);
		
		resultDao.addResult(submit.getResult());
		submitDao.addSubmit(submit);
	}

	public Submit prepareSubmit(User user, String submitFileName, Long problemId) {
		Submit submit = new Submit();
		submit.setCommitDate(getCurrentDate());
		submit.setFilePath(submitFileName);
		
		Problem problem = problemDao.getById(problemId);
		submit.setProblem(problem);
		
		Result result = prepareResult();
		submit.setResult(result);
		
		
		submit.setUser(user);
		return submit;
	}

	public Result prepareResult() { //TODO do fabryki
		Result result = new Result();
		result.setScore(new BigDecimal(0));
		result.setStatus(ResultStatusEnum.WAITING.toString());
		return result;
	}



	public Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

}

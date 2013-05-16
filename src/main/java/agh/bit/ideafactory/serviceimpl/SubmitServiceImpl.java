package agh.bit.ideafactory.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.model.ResultStatusEnum;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.SubmitService;

@Service
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private SubmitDao submitDao;
	
	@Autowired
	private ResultDao resultDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProblemDao problemDao;
	
	@Override
    @Transactional
	public void addSubmit(Submit submit) {
		submitDao.addSubmit(submit);		
	}

	@Override
	public List<Submit> getSubmitsByUser(User user) {
		return submitDao.getSubmitsByUser(user);
	}

	@Override
	public List<Submit> getSubmitsByProblem(Problem problem) {
		return submitDao.getSubmitsByProblem(problem);
	}

	@Override
	public void saveSubmitOnServer(MultipartFile file, User user, Long problemId ) throws IOException {
		FileOutputStream f = null;
		byte[] bytes = file.getBytes();
		String separator =  System.getProperty("file.separator");
		File submitFile = new File("p"); 
		String parentPath = submitFile.getCanonicalPath().substring(0, submitFile.getCanonicalPath().lastIndexOf(separator));
		
		parentPath = parentPath+separator+"Uploads"+separator+user.getUsername()+separator+"submits"+separator;
		String submitPath = parentPath +"submit_"+user.getSubmits().size();
		
		File parentFile = new File(parentPath);
		
		if (  !parentFile.exists() && !parentFile.mkdirs()) {
			System.err.println("Error");
			throw new IllegalStateException("Couldn't create dir: " + parentFile);
		}
		submitFile = new File(submitPath);
		if ( submitFile.exists() == false) submitFile.createNewFile();
		f = new FileOutputStream(submitFile);
		f.write(bytes);
		f.close();
		
		Submit submit = new Submit();
		submit.setCommitDate(Calendar.getInstance().getTime());
		submit.setFilePath(submitPath);
		
		Problem problem = problemDao.getById(problemId);
		submit.setProblem(problem);
		
		Result result = new Result();
		result.setScore(new BigDecimal(0));
		result.setStatus(ResultStatusEnum.WAITING.toString());
		submit.setResult(result);
		submit.setUser(user);
		List<Submit> submits = user.getSubmits();
		submits.add(submit);
		user.setSubmits(submits);
		
		resultDao.addResult(result);
		userDao.update(user);
		//addSubmit(submit);
	}

}

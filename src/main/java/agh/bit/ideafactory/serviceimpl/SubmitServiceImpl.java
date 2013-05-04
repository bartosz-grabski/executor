package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.SubmitService;

@Service
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private SubmitDao submitDao;
	
	@Override
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

}

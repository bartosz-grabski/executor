package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ProblemService;


@Service("problemService")
public class ProblemServiceImpl implements ProblemService {

	@Autowired
	private ProblemDao problemDao;

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
	
}

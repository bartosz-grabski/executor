package agh.bit.ideafactory.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {

	@Autowired
	private ResultDao resultDao;

	@Override
	@Transactional
	public void addResult(Result result) {
		resultDao.save(result);

	}

}

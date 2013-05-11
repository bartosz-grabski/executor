package agh.bit.ideafactory.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.model.Result;


@Service
public class ResultServiceImpl implements ResultDao {

	@Autowired
	private ResultDao resultDao;
	
	@Override
	public void addResult(Result result) {
		resultDao.addResult(result);
		
	}

	
	
}

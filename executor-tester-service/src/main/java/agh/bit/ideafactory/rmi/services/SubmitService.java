package agh.bit.ideafactory.rmi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.rmi.dao.SubmitDao;

@Service
@Transactional
public class SubmitService {
	
	@Autowired
	private SubmitDao submitDao;
	
	public Submit getSubmitById(Long id) {
		return submitDao.getSubmitById(id);
	}

}

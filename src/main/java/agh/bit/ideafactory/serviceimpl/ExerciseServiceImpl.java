package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.service.ExerciseService;

@Transactional
@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

	@Autowired
	private ExerciseDao exerciseDao;

	@Override
	public List<Exercise> findAll() {
		return exerciseDao.findAll();
	}

	@Override
	public Exercise getById(Long id) {
		return exerciseDao.findById(id);
	}

}

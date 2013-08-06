package agh.bit.ideafactory.daoimpl;

import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.model.Exercise;

@Repository("exerciseDao")
public class ExerciseDaoImpl extends BaseDaoImpl<Exercise> implements ExerciseDao {

}

package agh.bit.ideafactory.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.model.Exercise;

@Repository("exerciseDao")
public class ExerciseDaoImpl extends BaseDaoImpl<Exercise> implements ExerciseDao {

}

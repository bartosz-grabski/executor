package agh.bit.ideafactory.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.Problem;
import agh.bit.ideafactory.service.ExerciseService;

@Transactional
@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

	@Autowired
	private ExerciseDao exerciseDao;

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private GroupDao groupDao;

	@Override
	public List<Exercise> findAll() {
		return exerciseDao.findAll();
	}

	@Override
	public Exercise getById(Long id) {
		return exerciseDao.findById(id);
	}

	@Override
	public void update(Exercise exercise) {
		exerciseDao.update(exercise);

	}

	@Override
	public Exercise saveExercise(Exercise exercise, Long problemID) throws NotUniquePropertyException, NoObjectFoundException {

		Problem problem = problemDao.findById(problemID);

		if (problem == null) {
			throw new NoObjectFoundException(Problem.class, "No problem found");
		}

		for (Exercise problemExercise : problem.getExercises()) {
			if (problemExercise.getTitle().equals(exercise.getTitle())) {
				throw new NotUniquePropertyException("Exercise title must be unique among problem's exercises!", Exercise.class, "title");
			}
		}

		exercise.setProblem(problem);
		exercise.getTests().addAll(problem.getTests());

		problem.getExercises().add(exercise);
		exerciseDao.save(exercise);

		return exercise;
	}

	@Override
	public List<Exercise> getAllByProblem(Long problemId) {

		Problem problem = problemDao.findById(problemId);

		List<Exercise> result;

		if (problem == null) {
			result = new ArrayList<>();
		} else {
			problem.getExercises().size();
			result = problem.getExercises();
		}

		return result;
	}

	@Override
	public List<Exercise> getAllThatCanBeAddedToGroup(Long groupId, Long problemId) {

		List<Exercise> result = getAllByProblem(problemId);

		Group group = groupDao.findById(groupId);

		if (group != null) {
			Iterator<Exercise> iterator = result.iterator();
			while (iterator.hasNext()) {
				Exercise exercise = iterator.next();
				if (group.getExercises().contains(exercise)) {
					iterator.remove();
				}
			}
		}
		return result;

	}
}

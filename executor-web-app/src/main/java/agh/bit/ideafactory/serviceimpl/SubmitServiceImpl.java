package agh.bit.ideafactory.serviceimpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import agh.bit.ideafactory.dao.ExerciseDao;
import agh.bit.ideafactory.dao.ProblemDao;
import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.dao.SubmitDao;
import agh.bit.ideafactory.exception.SubmitLanguageException;
import agh.bit.ideafactory.helpers.FileManager;
import agh.bit.ideafactory.helpers.LanguageEnum;
import agh.bit.ideafactory.model.Exercise;
import agh.bit.ideafactory.model.Result;
import agh.bit.ideafactory.model.ResultStatusEnum;
import agh.bit.ideafactory.model.Submit;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.SubmitService;

@Service
@Transactional
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private FileManager fileManager;

	@Autowired
	public SubmitDao submitDao;

	@Autowired
	public ResultDao resultDao;

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private ExerciseDao exerciseDao;

	@Override
	public List<Submit> getSubmitsByUser(User user) {
		return submitDao.getSubmitsByUser(user);
	}

	@Override
	public Submit saveSubmitOnServer(MultipartFile submittedFile, User user, Long exerciseId, String languageName) throws IOException, SubmitLanguageException {

		LanguageEnum language = null;
		if (languageName != null)
			language = LanguageEnum.getLanguageByName(languageName);
		else {
			String fileExtension = submittedFile.getOriginalFilename().substring(submittedFile.getOriginalFilename().lastIndexOf('.') + 1);
			language = LanguageEnum.getLanguageByName(fileExtension);
		}

		if (language == null) {
			throw new SubmitLanguageException("Unsupported language exception");
		}

		String submitFileName = fileManager.getSubmitFileName(user, language);

		Submit submit = prepareSubmit(user, exerciseId, language, submitFileName);

		resultDao.save(submit.getResult());
		Submit savedSubmit = submitDao.saveSubmit(submit, submittedFile);
		return savedSubmit;
	}

	public Submit prepareSubmit(User user, Long exerciseId, LanguageEnum language, String submitFileName) {
		Submit submit = new Submit();
		submit.setCommitDate(getCurrentDate());

		Exercise exercise = exerciseDao.findById(exerciseId);
		submit.setExercise(exercise);

		Result result = prepareResult();
		submit.setResult(result);

		submit.setUser(user);

		submit.setLanguageEnum(language);

		submit.setFileName(submitFileName);

		return submit;
	}

	public Result prepareResult() { // TODO do fabryki
		Result result = new Result();
		result.setScore(new BigDecimal(0));
		result.setStatus(ResultStatusEnum.WAITING.toString());
		return result;
	}

	public Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	@Override
	public Submit findById(Long submitId) {
		return submitDao.findById(submitId);
	}

}

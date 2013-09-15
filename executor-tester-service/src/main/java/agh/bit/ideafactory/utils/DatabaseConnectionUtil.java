package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import agh.bit.ideafactory.rmi.mappers.SubmitRowMapper;
import agh.bit.ideafactory.rmi.mappers.TestIdsMapper;
import agh.bit.ideafactory.rmi.mappers.TestRowMapper;
import agh.bit.ideafactory.rmi.model.TestResult;
import agh.bit.ideafactory.rmi.model.Submit;
import agh.bit.ideafactory.rmi.model.Test;

/**
 * Utility class for handling i/o operations with the database
 * 
 * @author bgrabski
 * 
 */
@Configurable(autowire = Autowire.BY_NAME, dependencyCheck = true)
public class DatabaseConnectionUtil {

	/**
	 * Logger
	 */
	public static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionUtil.class);
	/**
	 * Query strings
	 */
	private static final String SUBMIT_SELECT_QUERY = "SELECT * FROM submit where submit_id = ?";
	private static final String TEST_SELECT_QUERY = "SELECT * FROM test where test_id in ?";
	private static final String TEST_IDS_SELECT_QUERY = "SELECT * FROM exercuse_test where exercise_exercise_id = ?";
	// private static final String RESULT_INSERT_QUERY = "INSERT "

	@Autowired
	private DataSource dataSource;

	/**
	 * Queries the database for submit
	 * 
	 * @param id
	 *            - id of submit to be fetched
	 * @return submit object
	 * @throws IllegalArgumentException
	 *             - no unique submit with given id was found
	 */
	public Submit getSubmit(int id) throws IllegalArgumentException {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		List<Submit> resultList = select.query(SUBMIT_SELECT_QUERY, new Object[] { id }, new SubmitRowMapper());
		if (resultList.size() == 0 || resultList.size() > 1) {
			throw new IllegalArgumentException("No unique submit with given id was found");
		}
		return resultList.get(0);
	}

	public List<Test> getTests(Submit submit) throws IllegalArgumentException {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		Long exerciseId = submit.getExerciseId();
		List<Long> testIds = getTestsIDsForExercise(exerciseId);
		String sqlList = getUrlSQLList(testIds);
		List<Test> resultList = select.query(TEST_SELECT_QUERY, new Object[] { sqlList }, new TestRowMapper());
		return resultList;
	}

	public void putResult(List<TestResult> testResultList, Long SubmitId) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("result").usingGeneratedKeyColumns("result_id");
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("score", 0);
		parameters.put("status", "OK");
		parameters.put("submit_id", SubmitId);
		Number resultId = insert.executeAndReturnKey(parameters);
		for (TestResult tr : testResultList) {
			putTestResult(tr, resultId.longValue());
		}
	}

	private void putTestResult(TestResult testResult, Long resultId) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("testresult").usingGeneratedKeyColumns("test_result_id");
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("score", 0);
		parameters.put("result_id", resultId);
		insert.execute(parameters);
	}
	
	public List<Long> getTestsIDsForExercise(Long exerciseId) {
		JdbcTemplate select = new JdbcTemplate(dataSource);
		List<Long> testIds = select.query(TEST_IDS_SELECT_QUERY, new Object[] { exerciseId }, new TestIdsMapper());
		return testIds;
	}

	
	private String getUrlSQLList(List<Long> ids) {
		String sep = " , ";
		StringBuffer sb = new StringBuffer();
		sb.append("( ");
		for(Long id : ids) {
			sb.append(id);
			sb.append(sep);
		}
		sb.setLength(sb.length()-1); //removing trailing separator
		sb.append(" )");
		return sb.toString();
	}
	/*
	 * public static void main(String[] argv) throws IOException, SQLException { getConnection().connect(); }
	 */

}

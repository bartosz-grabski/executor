package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
	private static final String TEST_SELECT_QUERY = "SELECT * FROM test where test_id = ?";
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
		// TODO getTests
		return null;
	}

	public void putResult(List<TestResult> testResultList, String SubmitId) {
		// TODO putResult
	}

	private void putTestResult(TestResult testResult, String SubmitId) {
		// TODO putTestResult
	}

	/*
	 * public static void main(String[] argv) throws IOException, SQLException { getConnection().connect(); }
	 */

}

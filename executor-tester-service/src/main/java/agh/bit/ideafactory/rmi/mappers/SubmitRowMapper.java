package agh.bit.ideafactory.rmi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import agh.bit.ideafactory.rmi.model.Submit;

/**
 * Class used by JDBC templates to map result set to an entity (Submit)
 * @author bgrabski
 *
 */
public class SubmitRowMapper implements RowMapper<Submit> {

	@Override
	public Submit mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Submit submit = new Submit();
		submit.setContent(rs.getBytes("content"));
		submit.setLanguage(rs.getString("language"));
		submit.setSubmitId(rs.getLong("submit_id"));
		submit.setExerciseId(rs.getLong("exercise_id"));
		return submit;
	}

}

package agh.bit.ideafactory.rmi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import agh.bit.ideafactory.rmi.model.Submit;

/**
 * Class used by JDBC templates to map result set to an entity
 * @author bgrabski
 *
 */
public class SubmitRowMapper implements RowMapper<Submit> {

	@Override
	public Submit mapRow(ResultSet rs, int line) throws SQLException {
		Submit submit = new Submit();
		submit.setPath(rs.getString("file_path"));
		return submit;
	}

}

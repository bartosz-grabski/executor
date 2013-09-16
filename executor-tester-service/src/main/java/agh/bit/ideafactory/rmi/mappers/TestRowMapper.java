package agh.bit.ideafactory.rmi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import agh.bit.ideafactory.rmi.model.Test;

/**
 * Mapper for Test entity
 * @author bgrabski
 *
 */
public class TestRowMapper implements RowMapper<Test> {

	@Override
	public Test mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Test test = new Test();
		test.setId(rs.getLong("test_id"));
		test.setInput(rs.getBytes("input"));
		test.setOutput(rs.getBytes("output"));
		return null;
	}

}

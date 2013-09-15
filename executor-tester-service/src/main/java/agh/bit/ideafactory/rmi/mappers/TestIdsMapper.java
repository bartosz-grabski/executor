package agh.bit.ideafactory.rmi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TestIdsMapper implements RowMapper<Long> {

	@Override
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getLong("tests_test_id");
	}

}

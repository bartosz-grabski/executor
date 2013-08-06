package agh.bit.ideafactory.test.main;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractTest {

	private final List<String> inserts = new ArrayList<String>();
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractTest() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/import.sql")));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				if (StringUtils.isNotEmpty(line) && !line.trim().startsWith("--")) {
					inserts.add(line);
				}
			}

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					Assert.fail(e1.getMessage());
				}
			}
		}

	}

	protected void clearDatabase(DataSource dataSource) throws Exception {

		Connection c = null;
		try {
			c = dataSource.getConnection();
			Statement s = null;
			try {
				s = c.createStatement();
				s.execute("SET DATABASE REFERENTIAL INTEGRITY FALSE");
				Set<String> tables = new HashSet<String>();
				ResultSet rs = s.executeQuery("select table_name " + "from INFORMATION_SCHEMA.system_tables " + "where table_type='TABLE' and table_schem='PUBLIC'");
				while (rs.next()) {
					if (!rs.getString(1).startsWith("DUAL_")) {
						tables.add(rs.getString(1));
					}
				}
				rs.close();
				for (String table : tables) {
					s.executeUpdate("DELETE FROM " + table);
				}
				s.execute("SET DATABASE REFERENTIAL INTEGRITY TRUE");

			} catch (SQLException e) {
				c.rollback();
				throw new Exception(e);
			} finally {
				if (s != null) {
					s.close();
				}
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	protected void initDB(DataSource dataSource) throws Exception {
		Connection c = null;
		String tmpInsert = null;
		try {
			c = dataSource.getConnection();
			Statement s = null;
			try {
				s = c.createStatement();
				for (String insert : inserts) {
					// TODO odpalić w debugu
					tmpInsert = insert;
					s.execute(insert);
				}
				s.close();
			} catch (SQLException e) {
				c.rollback();
				logger.error("Invalid insert: " + tmpInsert, e);
				throw new Exception(e);
			} finally {
				if (s != null) {
					s.close();
				}
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
}

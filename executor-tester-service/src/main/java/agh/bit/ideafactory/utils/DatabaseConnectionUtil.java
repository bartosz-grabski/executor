package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Utility class for handling i/o operations with database
 * 
 * @author bgrabski
 * 
 */
public class DatabaseConnectionUtil {

	/**
	 * Logger
	 */
	public static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionUtil.class);

	/**
	 * JDBC properties
	 */
	private String host;
	private String port;
	private String username;
	private String password;
	private String database;
	private String driverClass;
	private String type;
	private String connectionUrl;

	private static DatabaseConnectionUtil connection = null;

	private DatabaseConnectionUtil() throws IOException {
		loadFromPropertiesFile();
		connectionUrl = getConnectionUrl();

	}

	public static DatabaseConnectionUtil getConnection() throws IOException {
		if (connection == null)
			connection = new DatabaseConnectionUtil();
		return connection;
	}

	/**
	 * @throws IOException
	 *             Error while reading properties file
	 * 
	 */
	private void loadFromPropertiesFile() throws IOException {
		loadFromPropertiesFile(Const.PROP_FILE_NAME);
	}

	private void loadFromPropertiesFile(String filename) throws IOException {
		logger.debug("Loading properties from file " + filename);
		Properties props = new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream(filename));

		this.driverClass = props.getProperty(Const.PROP_DRIVER_PROP_NAME);
		this.username = props.getProperty(Const.PROP_USERNAME_PROP_NAME);
		this.password = props.getProperty(Const.PROP_PASSWORD_PROP_NAME);
		this.host = props.getProperty(Const.PROP_HOST_PROP_NAME);
		this.port = props.getProperty(Const.PROP_PORT_PROP_NAME);
		this.database = props.getProperty(Const.PROP_DB_PROP_NAME);
		this.type = props.getProperty(Const.PROP_TYPE_PROP_NAME);
	}

	private String getConnectionUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:");
		sb.append(type);
		sb.append("://"); // hostname:port/dbname","username", "password");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(database);
		return sb.toString();
	}

	public void connect() throws SQLException {
		logger.debug("Attempting connection to " + connectionUrl);
		DriverManager.getConnection(connectionUrl, username, password);
		logger.debug("Connection successful");
	}

	public static void main(String[] argv) throws IOException, SQLException {
		getConnection().connect();
	}

}

package agh.bit.ideafactory.rmi.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Utility class for obtaining properties values from file
 * @author bgrabski
 *
 */
public class Props {
	
	
	private static Logger logger = LoggerFactory.getLogger(Props.class);
	private static Properties properties = new Properties();
	private static Props props = new Props();
	
	protected Props(String filename) {
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(filename));
		} catch (IOException e) {
			logger.debug("Loading properties file failed");
			e.printStackTrace();
		}
	}
	
	protected Props() {
		this("tester.properties");
	}
	
	
	public static int getIntProperty(String propertyName, int def) {
		String prop = properties.getProperty(propertyName);
		if (prop != null) {
			try {
				return Integer.parseInt(prop);
			} catch (NumberFormatException e) {
				return def;
			}
		} else {
			return def;
		}
	}
	
	public static int getIntProperty(String propertyName) {
		return getIntProperty(propertyName,0);
	}
	
	public static String getStringProperty(String propertyName, String def) {
		String prop = properties.getProperty(propertyName);
		if (prop == null) {
			return def;
		}
		return prop;
	}
	
	public static String getStringProperty(String propertyName) {
		return getStringProperty(propertyName, "");
	}
	
	
	public static String getHostProperty() {
		return getStringProperty("agh.bit.ideafactory.tester.host");
	}
	
	public static int getPortProperty() {
		return getIntProperty("agh.bit.ideafactory.tester.port");
	}

}

package agh.bit.ideafactory.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

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

}

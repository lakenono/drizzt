package drizzt.conf;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang3.StringUtils;

public class Configuration {

	private static org.apache.commons.configuration.Configuration config;

	static {
		try {
			config = new XMLConfiguration("config.xml");
		} catch (ConfigurationException e) {
			throw new RuntimeException("config.xml read error : ", e);
		}
	}

	public static String getString(String key) {
		return config.getString(key);
	}

	public static String getString(String key, String defaultValue) {
		String value = config.getString(key);
		return StringUtils.isNotBlank(value) ? value : defaultValue;
	}

	public static void main(String[] args) {
		System.out.println(Configuration.getString("doddata-test.cleandb"));
	}
}

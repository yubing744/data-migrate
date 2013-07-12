package org.yubing.datmv.util.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesConfigLoader implements ConfigLoader {

	private static final String PROPERTIES_FILE = "settings.properties";

	public Map<String, String> load() {

		Properties prop = new Properties();

		try {
			prop.load(this.getClass()
					.getResourceAsStream("/" + PROPERTIES_FILE));

		} catch (IOException e) {
			throw new RuntimeException("Error in load File " + PROPERTIES_FILE);
		}

		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> propNames = prop.propertyNames();

		while (propNames.hasMoreElements()) {
			Object obj = propNames.nextElement();
			if (obj instanceof String) {
				String propName = (String) obj;
				map.put(propName, prop.getProperty(propName));
			}
		}

		return map;
	}

}

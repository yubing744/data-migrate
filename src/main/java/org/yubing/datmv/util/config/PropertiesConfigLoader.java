package org.yubing.datmv.util.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.yubing.datmv.util.ResourceUtils;

public class PropertiesConfigLoader implements ConfigLoader {
	private String path;
	private boolean optional;
	
	public PropertiesConfigLoader(String path) {
		this.path = path;
		this.optional = false;
	}
	
	public PropertiesConfigLoader(String file, boolean optional) {
		this.path = file;
		this.optional = optional;	
	}
	
	public Map<String, String> load() {
		Properties prop = new Properties();

		try {
			prop.load(ResourceUtils.openResource(this.path));
		} catch (Exception e) {
			if (!this.optional) {
				throw new RuntimeException("Error load config file in path: " + this.path);
			}
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

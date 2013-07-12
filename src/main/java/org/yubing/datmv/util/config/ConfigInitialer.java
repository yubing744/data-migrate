package org.yubing.datmv.util.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.yubing.datmv.util.ConfigUtils;

public class ConfigInitialer {

	private List<ConfigLoader> configLoaders = new ArrayList<ConfigLoader>();

	private ConfigInitialer() {
	}

	public static void init() {
		ConfigInitialer cfgIniter = new ConfigInitialer();
		cfgIniter.initConfigLoader();
		cfgIniter.parseConfigLoader();

		String userDir = System.getProperty("user.dir");
		Configuration.putValue("base.dir", userDir);
	}

	protected void initConfigLoader() {
		configLoaders.add(new PropertiesConfigLoader());
	}

	protected void parseConfigLoader() {
		for (ConfigLoader loader : configLoaders) {
			Map<String, String> configMap = loader.load();

			for (Entry<String, String> entry : configMap.entrySet()) {
				String value = entry.getValue();
				Configuration.putValue(entry.getKey(), ConfigUtils
						.handleRefVal(value));
			}
		}
	}
}

package org.yubing.datmv.util.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 *	配置初始化器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public class ConfigInitialer {

	private static final String DEFAULT_PROPERTIES_FILE = "classpath:/default_migrate_settings.properties";

	private static final String USER_PROPERTIES_FILE2 = "classpath:/migrate.properties";
	private static final String USER_PROPERTIES_FILE3 = "file:./migrate.properties";
	
	private List<ConfigLoader> configLoaders = new ArrayList<ConfigLoader>();
	private static boolean isLoad = false;
	
	private ConfigInitialer() {
		super();
	}

	public static void init() {
		if (!isLoad) {
			ConfigInitialer cfgIniter = new ConfigInitialer();
			cfgIniter.initConfigLoader();
			cfgIniter.parseConfigLoader();
	
			String userDir = System.getProperty("user.dir");
			Configuration.putValue("base.dir", userDir);
			isLoad = true;
		}
	}

	protected void initConfigLoader() {
		configLoaders.add(new PropertiesConfigLoader(DEFAULT_PROPERTIES_FILE));
		configLoaders.add(new PropertiesConfigLoader(USER_PROPERTIES_FILE2, true));
		configLoaders.add(new PropertiesConfigLoader(USER_PROPERTIES_FILE3, true));
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

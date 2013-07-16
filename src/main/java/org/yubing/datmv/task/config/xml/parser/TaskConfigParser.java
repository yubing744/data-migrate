package org.yubing.datmv.task.config.xml.parser;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.task.MigrateTask;
import org.yubing.datmv.task.config.xml.ConfigParser;
import org.yubing.datmv.task.config.xml.XmlMigrateTasksConfig;
import org.yubing.datmv.util.DocumentUtils;


/**
 *	任务配置解析器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public class TaskConfigParser implements ConfigParser {
	public static final String TAG_MIGRATE_CONFIG = "migrate-config";

	public static final String ATTR_MIGRATE_CONFIG = "migrate-config";
	public static final String ATTR_INCLUDE = "include";

	
	public void parse(XmlMigrateTasksConfig tasksConfig, Element element) {
		MigrateTask task = new MigrateTask();
		
		String name = DocumentUtils.findAttrByName(element,
				ATTR_KEY, new String[]{"value", "type"});
		if (!StringUtils.isBlank(name)) {
			task.setName(name);
		}
		
		String migrateConfigFile = DocumentUtils.findAttrByName(element,
				ATTR_MIGRATE_CONFIG, new String[]{ATTR_INCLUDE});
		if (!StringUtils.isBlank(migrateConfigFile)) {
			XmlMigrateConfig xmlMigrateConfig = new XmlMigrateConfig(migrateConfigFile);
			task.setMigrateConfig(xmlMigrateConfig);
		}
		
		tasksConfig.getMigrateTasks().add(task);
	}

}

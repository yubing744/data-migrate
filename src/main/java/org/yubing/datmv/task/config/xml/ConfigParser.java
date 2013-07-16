package org.yubing.datmv.task.config.xml;

import org.w3c.dom.Element;

public interface ConfigParser {
	
	static final String ATTR_KEY = "name";
	static final String ATTR_VALUE = "value";
	static final String ATTR_CLASS = "class";
	static final String ATTR_TYPE = "type";
	
	void parse(XmlMigrateTasksConfig tasksConfig, Element ele);
}

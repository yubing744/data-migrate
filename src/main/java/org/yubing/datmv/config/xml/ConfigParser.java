package org.yubing.datmv.config.xml;

import org.w3c.dom.Element;

/**
 * XML 配置解析器
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-10
 */
public interface ConfigParser {

	public static final String ATTR_KEY = "name";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_CLASS = "class";
	public static final String ATTR_TYPE = "type";
	
	void parse(XmlMigrateConfig xmlMigrateConfig, Element ele);
}

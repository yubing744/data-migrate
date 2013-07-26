package org.yubing.datmv.config.xml;

import org.w3c.dom.Element;

/**
 * 迁移类型构造参数解析接口
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public interface ArgmentsParser {
	
	public static final String ATTR_KEY = "name";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_CLASS = "class";
	public static final String ATTR_TYPE = "type";
	
	/**
	 * 解析构造参数
	 * 
	 * @param element
	 * @return
	 */
	Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig, Element element);
}

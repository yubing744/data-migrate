package org.yubing.datmv.config.xml.parser.argment;

import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;

/**
 * IScritpMappingHandler 构造参数解析器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class ScriptBlockArgmentParser implements ArgmentsParser {

	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig, Element element) {
		String script = "";
		
		if (element != null) {
			script = element.getTextContent();
		}
		
		return new Object[] { script };
	}

}

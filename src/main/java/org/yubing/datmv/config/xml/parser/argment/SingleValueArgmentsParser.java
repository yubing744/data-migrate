package org.yubing.datmv.config.xml.parser.argment;

import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

public class SingleValueArgmentsParser implements ArgmentsParser {

	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig,
			Element element) {
		String script = "";
		
		if (element != null) {
			script = DocumentUtils.findAttrByName(element, ArgmentsParser.ATTR_VALUE, new String[]{"value"});
		}
		
		return new Object[] { script };
	}

}

package org.yubing.datmv.config.xml.parser.argment;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;

public class MultiStringArgmentsParser implements ArgmentsParser {

	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig, Element element) {
		List<String> argList = new ArrayList<String>();
		if (element != null) {
			NodeList nodeList = element.getChildNodes();
			int size = nodeList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = nodeList.item(i);
				if (node instanceof Element) {
					Element ele = (Element) node;
					String argValue = ele.getAttribute(ConfigParser.ATTR_VALUE);
					argList.add(argValue);
				}
			}
		}
		Object[] args = new Object[argList.size()];
		argList.toArray(args);
		return args;
	}

}

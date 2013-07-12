package org.yubing.datmv.config.xml.parser.argment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

public class InitParamsArgmentsParser implements ArgmentsParser {
	public static final String TAG_INIT_PARAM = "init-param";

	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig, Element element) {
		Map<String, String> initParams = new HashMap<String, String>();
		
		if (element != null) {
			NodeList nodeList = element.getElementsByTagName(TAG_INIT_PARAM);
			int size = nodeList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = nodeList.item(i);
				if (node instanceof Element) {
					Element ele = (Element) node;
					String name = DocumentUtils.findAttrByName(ele,
							ConfigParser.ATTR_KEY);
					if (!StringUtils.isBlank(name)) {
						String value = DocumentUtils.findAttrByName(ele,
								ConfigParser.ATTR_VALUE);
						initParams.put(name, value);
					}
				}
			}
		}

		return new Object[] { initParams };
	}

}

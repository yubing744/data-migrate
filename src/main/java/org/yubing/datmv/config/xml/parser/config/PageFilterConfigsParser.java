package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.FilterConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

public class PageFilterConfigsParser extends FilterConfigsParser {

	public static final String TAG_TYPE = "page-filter";
	public static final String TAG_FILTER_CLASS = "filter";
	public static final String TAG_CONSTRUCTOR_ARG_PARSER_CLASS = "constructor-arg-parser";

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element ele) {
		if (ele != null) {
			NodeList typeList = ele.getElementsByTagName(TAG_TYPE);
			int size = typeList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = typeList.item(i);
				if (node instanceof Element) {
					Element subEle = (Element) node;
					parsePageFilter(xmlMigrateConfig, subEle);
				}
			}
		}
	}

	public void parsePageFilter(XmlMigrateConfig xmlMigrateConfig, Element ele) {
		String name = ele.getAttribute(ConfigParser.ATTR_KEY);
		if (!StringUtils.isBlank(name)) {
			FilterConfig config = new FilterConfig(name);

			Element filterEle = DocumentUtils.findOneElementByTagName(ele,
					TAG_FILTER_CLASS);
			if (filterEle != null) {
				String clazz = filterEle.getAttribute(ATTR_CLASS);
				config.setFilter(clazz);
			}

			Element argParserEle = DocumentUtils.findOneElementByTagName(ele,
					TAG_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				config.setConstructorArgParser(clazz);
			}

			xmlMigrateConfig.addPageFilterConfig(config.getName(), config);
		}
	}
}


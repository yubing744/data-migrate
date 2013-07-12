package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.MappingHandlerConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

/**
 * 解析映射处理器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class MappingHandlersConfigParser implements ConfigParser {

	public static final String TAG_TYPE = "mapping-handler";
	public static final String TAG_HANDLER_CLASS = "handler";
	public static final String TAG_CONSTRUCTOR_ARG_PARSER_CLASS = "constructor-arg-parser";

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element mappingEle) {
		if (mappingEle != null) {
			NodeList handlerList = mappingEle.getElementsByTagName(TAG_TYPE);
			int size = handlerList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = handlerList.item(i);
				if (node instanceof Element) {
					Element mappingHandlerEle = (Element) node;
					parseMappingHandler(xmlMigrateConfig, mappingHandlerEle);
				}
			}
		}
	}

	public void parseMappingHandler(XmlMigrateConfig xmlMigrateConfig,
			Element mappingHandlerEle) {
		String name = mappingHandlerEle.getAttribute(ATTR_KEY);
		if (!StringUtils.isBlank(name)) {
			MappingHandlerConfig mapConfig = new MappingHandlerConfig(name);

			Element handlerEle = DocumentUtils.findOneElementByTagName(
					mappingHandlerEle, TAG_HANDLER_CLASS);
			if (handlerEle != null) {
				String clazz = handlerEle.getAttribute(ATTR_CLASS);
				mapConfig.setHandler(clazz);
			} else {
				throw new RuntimeException("Error in paser:" + TAG_TYPE + " "
						+ name);
			}

			Element argParserEle = DocumentUtils.findOneElementByTagName(
					mappingHandlerEle, TAG_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				mapConfig.setConstructorArgParser(clazz);
			}

			xmlMigrateConfig.addMappingHandler(mapConfig.getName(), mapConfig);
		}
	}

}

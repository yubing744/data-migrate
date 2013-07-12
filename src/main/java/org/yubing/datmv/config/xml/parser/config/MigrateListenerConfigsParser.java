package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.MigrateListenerConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

/**
 * 数据迁移监听器配置解析
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-11
 */
public class MigrateListenerConfigsParser implements ConfigParser {

	public static final String TAG_TYPE = "migrate-listener";
	public static final String TAG_LISTENER_CLASS = "listener";
	public static final String TAG_CONSTRUCTOR_ARG_PARSER_CLASS = "constructor-arg-parser";

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element ele) {
		if (ele != null) {
			NodeList typeList = ele.getElementsByTagName(TAG_TYPE);
			int size = typeList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = typeList.item(i);
				if (node instanceof Element) {
					Element subEle = (Element) node;
					parseMigrateListener(xmlMigrateConfig, subEle);
				}
			}
		}
	}

	public void parseMigrateListener(XmlMigrateConfig xmlMigrateConfig,
			Element ele) {
		String name = ele.getAttribute(ATTR_KEY);
		if (!StringUtils.isBlank(name)) {
			MigrateListenerConfig config = new MigrateListenerConfig(name);

			Element filterEle = DocumentUtils.findOneElementByTagName(ele,
					TAG_LISTENER_CLASS);
			if (filterEle != null) {
				String clazz = filterEle.getAttribute(ATTR_CLASS);
				config.setListener(clazz);
			}

			Element argParserEle = DocumentUtils.findOneElementByTagName(ele,
					TAG_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				config.setConstructorArgParser(clazz);
			}

			xmlMigrateConfig.addMigrateListenerConfig(config.getName(), config);
		}
	}

}

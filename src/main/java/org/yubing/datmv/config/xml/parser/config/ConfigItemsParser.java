package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.MappingHandlerConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.internal.SimpleConfigItem;
import org.yubing.datmv.util.DocumentUtils;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-10
 */
public class ConfigItemsParser implements ConfigParser {

	public static final String TAG_TYPE = "item";

	public static final String ATTR_MAPPING_KEY = "mapping-key";
	public static final String ATTR_HANDLER = "handler";

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element itemsEle) {
		if (itemsEle != null) {
			NodeList itemsList = itemsEle.getElementsByTagName(TAG_TYPE);
			int size = itemsList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = itemsList.item(i);
				if (node instanceof Element) {
					Element itemEle = (Element) node;
					parseConfigItem(xmlMigrateConfig, itemEle);
				}
			}
		}

	}

	/**
	 * 解析配置项
	 * 
	 * @param xmlMigrateConfig
	 * @param itemEle
	 */
	protected void parseConfigItem(XmlMigrateConfig xmlMigrateConfig,
			Element itemEle) {
		String name = itemEle.getAttribute(ATTR_KEY);
		if (!StringUtils.isBlank(name)) {
			SimpleConfigItem configItem = new SimpleConfigItem(name);

			String type = DocumentUtils.findAttrByName(itemEle, ATTR_TYPE);
			if (StringUtils.isBlank(type)) {
				type = DataType.STRING;
			}
			configItem.setType(type);

			String mappingKey = DocumentUtils.findAttrByName(itemEle,
					ATTR_MAPPING_KEY);
			if (!StringUtils.isBlank(mappingKey)) {
				configItem.setMappingKey(mappingKey);
			}

			MappingHandler mappingHandler = parseMappingHandler(
					xmlMigrateConfig, itemEle);
			if (mappingHandler != null) {
				configItem.setMappingHandler(mappingHandler);
			}

			if (StringUtils.isBlank(mappingKey) && mappingHandler == null) {
				throw new RuntimeException("Error in paser " + itemEle + ", "
						+ ATTR_MAPPING_KEY + " and " + ATTR_HANDLER
						+ " must have one.");
			}

			xmlMigrateConfig.addConfigItem(configItem);
		}

	}

	/**
	 * 解析映射处理器
	 * 
	 * @param xmlMigrateConfig
	 * @param itemEle
	 * @return
	 */
	protected MappingHandler parseMappingHandler(
			XmlMigrateConfig xmlMigrateConfig, Element itemEle) {

		String handler = DocumentUtils.findAttrByName(itemEle, ATTR_HANDLER);
		if (!StringUtils.isBlank(handler)) {
			Object[] args = {};
			String handlerClass = handler;

			MappingHandlerConfig mapConfig = xmlMigrateConfig
					.getMappingHandler(handler);
			if (mapConfig != null) {
				handlerClass = mapConfig.getHandler();
				args = findHandlerArgsFromMapConfig(xmlMigrateConfig, itemEle,
						mapConfig);
			}

			if (!StringUtils.isBlank(handlerClass)) {
				MappingHandler mappingHandler = (MappingHandler) ReflectUtils
						.newInstance(handlerClass, args);
				return mappingHandler;
			}
		}

		return null;
	}

	/**
	 * 生成处理参数
	 * 
	 * @param xmlMigrateConfig
	 * @param itemEle
	 * @param mapConfig
	 * @return
	 */
	public Object[] findHandlerArgsFromMapConfig(
			XmlMigrateConfig xmlMigrateConfig, Element itemEle,
			MappingHandlerConfig mapConfig) {
		Object[] args = {};

		String argParserClass = mapConfig.getConstructorArgParser();
		if (!StringUtils.isBlank(argParserClass)) {
			ArgmentsParser argParser = (ArgmentsParser) ReflectUtils
					.newInstance(argParserClass);
			if (argParser != null) {
				Element handlerEle = DocumentUtils.findOneElementByTagName(
						itemEle, ATTR_HANDLER);
				if (handlerEle != null) {
					args = argParser.parserArgs(xmlMigrateConfig, handlerEle);
				}
			}
		}

		return args;
	}
}

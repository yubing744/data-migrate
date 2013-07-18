package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.MigrateTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

/**
 * 迁移类型配置解析
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class MigrateTypeConfigsParser implements ConfigParser {

	public static final String TAG_TYPE = "migrate-type";
	public static final String TAG_READER_CLASS = "reader";
	public static final String TAG_WRITER_CLASS = "writer";
	
	public static final String TAG_CONSTRUCTOR_ARG_PARSER_CLASS = "constructor-arg-parser";
	public static final String TAG_READER_CONSTRUCTOR_ARG_PARSER_CLASS = "reader-constructor-arg-parser";
	public static final String TAG_WRITER_CONSTRUCTOR_ARG_PARSER_CLASS = "writer-constructor-arg-parser";
	
	public static final String TAG_PROPERTIY_PARSER_CLASS = "property-parser";
	
	public void parse(XmlMigrateConfig xmlMigrateConfig, Element typesEle) {
		if (typesEle != null) {
			NodeList typeList = typesEle.getElementsByTagName(TAG_TYPE);
			int size = typeList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = typeList.item(i);
				if (node instanceof Element) {
					Element typeEle = (Element) node;
					parseMigrateType(xmlMigrateConfig, typeEle);
				}
			}
		}
	}

	public void parseMigrateType(XmlMigrateConfig xmlMigrateConfig,
			Element typeEle) {
		String name = typeEle.getAttribute(ATTR_KEY);
		if (!StringUtils.isBlank(name)) {
			MigrateTypeConfig typeConfig = new MigrateTypeConfig(name);

			Element readerEle = DocumentUtils.findOneElementByTagName(typeEle,
					TAG_READER_CLASS);
			if (readerEle != null) {
				String clazz = readerEle.getAttribute(ATTR_CLASS);
				typeConfig.setReader(clazz);
			}

			Element writerEle = DocumentUtils.findOneElementByTagName(typeEle,
					TAG_WRITER_CLASS);
			if (writerEle != null) {
				String clazz = writerEle.getAttribute(ATTR_CLASS);
				typeConfig.setWriter(clazz);
			}

			Element argParserEle = DocumentUtils.findOneElementByTagName(
					typeEle, TAG_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				typeConfig.setConstructorArgParser(clazz);
			}

			
			argParserEle = DocumentUtils.findOneElementByTagName(
					typeEle, TAG_READER_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				typeConfig.setReaderConstructorArgParser(clazz);
			}
			
			argParserEle = DocumentUtils.findOneElementByTagName(
					typeEle, TAG_WRITER_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				typeConfig.setWriterConstructorArgParser(clazz);
			}
			
			Element propParserEle = DocumentUtils.findOneElementByTagName(
					typeEle, TAG_PROPERTIY_PARSER_CLASS);
			if (propParserEle != null) {
				String clazz = propParserEle.getAttribute(ATTR_CLASS);
				typeConfig.setPropertyParser(clazz);
			}
			
			xmlMigrateConfig.addMigrateTypeConfig(typeConfig.getName(),
					typeConfig);
		}
	}

}

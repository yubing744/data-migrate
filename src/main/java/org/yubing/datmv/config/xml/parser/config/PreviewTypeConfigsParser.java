package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.PreviewTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DocumentUtils;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-19
 */
public class PreviewTypeConfigsParser implements ConfigParser {

	public static final String TAG_TYPE = "preview-type";
	public static final String TAG_PAGE_PREVIEW_CLASS = "page-preview";
	public static final String TAG_CONSTRUCTOR_ARG_PARSER_CLASS = "constructor-arg-parser";

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element typesEle) {
		if (typesEle != null) {
			NodeList typeList = typesEle.getElementsByTagName(TAG_TYPE);
			int size = typeList.getLength();

			for (int i = 0; i < size; i++) {
				Node node = typeList.item(i);
				if (node instanceof Element) {
					Element typeEle = (Element) node;
					parsePreviewType(xmlMigrateConfig, typeEle);
				}
			}
		}
	}

	public void parsePreviewType(XmlMigrateConfig xmlMigrateConfig,
			Element typeEle) {
		String name = typeEle.getAttribute(ATTR_KEY);
		if (!StringUtils.isBlank(name)) {
			PreviewTypeConfig config = new PreviewTypeConfig(name);

			Element pagePreviewEle = DocumentUtils.findOneElementByTagName(
					typeEle, TAG_PAGE_PREVIEW_CLASS);
			if (pagePreviewEle != null) {
				String clazz = pagePreviewEle.getAttribute(ATTR_CLASS);
				config.setPagePreview(clazz);
			}

			Element argParserEle = DocumentUtils.findOneElementByTagName(
					typeEle, TAG_CONSTRUCTOR_ARG_PARSER_CLASS);
			if (argParserEle != null) {
				String clazz = argParserEle.getAttribute(ATTR_CLASS);
				config.setConstructorArgParser(clazz);
			}

			xmlMigrateConfig.addPreviewTypeConfig(config.getName(), config);
		}
	}

}

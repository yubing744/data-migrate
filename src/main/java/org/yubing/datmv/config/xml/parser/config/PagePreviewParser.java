package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.PreviewTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-19
 */
public class PagePreviewParser implements ConfigParser {

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element ele) {
		if (ele != null) {
			String pagePreview = ele.getAttribute(ATTR_TYPE);
			if (!StringUtils.isBlank(pagePreview)) {
				Object[] args = {};
				String clazz = pagePreview;

				PreviewTypeConfig config = xmlMigrateConfig
						.getPreviewTypeConfig(pagePreview);

				if (config != null) {
					args = makeArgs(xmlMigrateConfig, ele, config);
					clazz = config.getPagePreview();
				}

				if (!StringUtils.isBlank(clazz)) {
					PagePreview preview = (PagePreview) ReflectUtils
							.newInstance(clazz, args);
					xmlMigrateConfig.setPagePreview(preview);
				}

			}// end if (!StringUtils.isBlank(listener))
		}// end if (ele != null)
	}

	private Object[] makeArgs(XmlMigrateConfig xmlMigrateConfig, Element ele,
			PreviewTypeConfig config) {
		Object[] args = {};
		String argParserClass = config.getConstructorArgParser();
		if (!StringUtils.isBlank(argParserClass)) {
			ArgmentsParser argParser = (ArgmentsParser) ReflectUtils
					.newInstance(argParserClass);
			if (argParser != null) {
				args = argParser.parserArgs(xmlMigrateConfig, ele);
			}
		}
		return args;
	}

}

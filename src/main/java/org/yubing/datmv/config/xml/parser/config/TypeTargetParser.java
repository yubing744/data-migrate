package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.MigrateTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.util.ReflectUtils;
import org.yubing.datmv.util.config.ConfigUtils;

/**
 * 描述
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-10
 */
public class TypeTargetParser extends MigrateTypeParser {

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element sourceEle) {
		if (sourceEle != null) {
			String typeName = sourceEle.getAttribute(ATTR_TYPE);
			if (!StringUtils.isBlank(typeName)) {
				MigrateTypeConfig typeConfig = xmlMigrateConfig
						.getMigrateTypeConfig(typeName);

				String value = sourceEle.getAttribute(ATTR_VALUE);
				value = ConfigUtils.handleRefVal(value);
				Object[] args = { value };

				String argParserClass = typeConfig.getWriterConstructorArgParser();
				if (StringUtils.isBlank(argParserClass)) {
					argParserClass = typeConfig.getConstructorArgParser();
				}

				if (!StringUtils.isBlank(argParserClass)) {
					ArgmentsParser argParser = (ArgmentsParser) ReflectUtils
							.newInstance(argParserClass);
					if (argParser != null) {
						args = argParser.parserArgs(xmlMigrateConfig, sourceEle);
					}
				}
				
				parseType(xmlMigrateConfig, typeConfig, args);
			}
		}
	}
	
	public void parseType(XmlMigrateConfig xmlMigrateConfig,
			MigrateTypeConfig typeConfig, Object[] args) {
		String writerClass = typeConfig.getWriter();
		if (!StringUtils.isBlank(writerClass)) {
			PageWriter targetWriter = (PageWriter) ReflectUtils.newInstance(
					writerClass, args);
			if (targetWriter != null) {
				xmlMigrateConfig.setTargetWriter(targetWriter);
			}
		}
	}
}

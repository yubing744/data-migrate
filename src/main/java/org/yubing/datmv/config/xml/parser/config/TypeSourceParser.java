package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.MigrateTypeConfig;
import org.yubing.datmv.config.xml.PropertyParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.util.ReflectUtils;
import org.yubing.datmv.util.config.ConfigUtils;

/**
 * 描述
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class TypeSourceParser extends MigrateTypeParser {

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element sourceEle) {
		if (sourceEle != null) {
			String typeName = sourceEle.getAttribute(ATTR_TYPE);
			if (!StringUtils.isBlank(typeName)) {
				MigrateTypeConfig typeConfig = xmlMigrateConfig
						.getMigrateTypeConfig(typeName);

				String value = sourceEle.getAttribute(ATTR_VALUE);
				value = ConfigUtils.handleRefVal(value);
				Object[] args = { value };

				String argParserClass = typeConfig.getReaderConstructorArgParser();
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
				
				parseType(xmlMigrateConfig, sourceEle, typeConfig, args);
			}
		}
	}
	
	public void parseType(XmlMigrateConfig xmlMigrateConfig, Element sourceEle,
			MigrateTypeConfig typeConfig, Object[] args) {
		String readerClass = typeConfig.getReader();
		if (!StringUtils.isBlank(readerClass)) {
			PageReader sourceReader = (PageReader) ReflectUtils.newInstance(
					readerClass, args);
			if (sourceReader != null) {
				xmlMigrateConfig.setSourceReader(sourceReader);
			}
			
			String propParserClass = typeConfig.getPropertyParser();

			if (!StringUtils.isBlank(propParserClass)) {
				PropertyParser propParser = (PropertyParser) ReflectUtils
						.newInstance(propParserClass);
				if (propParser != null) {
					propParser.parserProperties(xmlMigrateConfig, sourceReader, sourceEle);
				}
			}
			
		}
	}
}

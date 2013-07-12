package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.RecordFilterConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.RecordFilter;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 记录过滤处理器
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-11
 */
public class RecordFilterParser implements ConfigParser {

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element ele) {
		if (ele != null) {
			String filter = ele.getAttribute(ATTR_VALUE);
			if (!StringUtils.isBlank(filter)) {
				Object[] args = {};
				String filterClazz = filter;

				RecordFilterConfig config = xmlMigrateConfig
						.getRecordFilterConfig(filter);

				if (config != null) {
					args = makeArgs(xmlMigrateConfig, ele, config);
					filterClazz = config.getFilter();
				}

				if (!StringUtils.isBlank(filterClazz)) {
					RecordFilter recordFilter = (RecordFilter) ReflectUtils
							.newInstance(filterClazz, args);
					xmlMigrateConfig.addRecordFilter(recordFilter);
				}

			}// end if (!StringUtils.isBlank(filter))
		}// end if (ele != null)
	}

	private Object[] makeArgs(XmlMigrateConfig xmlMigrateConfig, Element ele,
			RecordFilterConfig config) {
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

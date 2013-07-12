package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.ConfigParser;
import org.yubing.datmv.config.xml.MigrateListenerConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.MigrateListener;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 数据迁移监听器解析
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-11
 */
public class MigrateListenerParser implements ConfigParser {

	public void parse(XmlMigrateConfig xmlMigrateConfig, Element ele) {
		if (ele != null) {
			String listener = ele.getAttribute(ATTR_VALUE);
			if (!StringUtils.isBlank(listener)) {
				Object[] args = {};
				String listenerClazz = listener;

				MigrateListenerConfig config = xmlMigrateConfig
						.getMigrateListenerConfig(listener);

				if (config != null) {
					args = makeArgs(xmlMigrateConfig, ele, config);
					listenerClazz = config.getListener();
				}

				if (!StringUtils.isBlank(listenerClazz)) {
					MigrateListener migrateListener = (MigrateListener) ReflectUtils
							.newInstance(listenerClazz, args);
					xmlMigrateConfig.addMigrateListener(migrateListener);
				}

			}// end if (!StringUtils.isBlank(listener))
		}// end if (ele != null)
	}

	private Object[] makeArgs(XmlMigrateConfig xmlMigrateConfig, Element ele,
			MigrateListenerConfig config) {
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

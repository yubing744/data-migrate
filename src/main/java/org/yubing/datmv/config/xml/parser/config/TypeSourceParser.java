package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.config.xml.MigrateTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 描述
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class TypeSourceParser extends MigrateTypeParser {

	public void parseType(XmlMigrateConfig xmlMigrateConfig,
			MigrateTypeConfig typeConfig, Object[] args) {
		String readerClass = typeConfig.getReader();
		if (!StringUtils.isBlank(readerClass)) {
			PageReader sourceReader = (PageReader) ReflectUtils.newInstance(
					readerClass, args);
			if (sourceReader != null) {
				xmlMigrateConfig.setSourceReader(sourceReader);
			}
		}
	}
}

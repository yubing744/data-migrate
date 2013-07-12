package org.yubing.datmv.config.xml.parser.config;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.config.xml.MigrateTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 描述
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-10
 */
public class TypeTargetParser extends MigrateTypeParser {

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

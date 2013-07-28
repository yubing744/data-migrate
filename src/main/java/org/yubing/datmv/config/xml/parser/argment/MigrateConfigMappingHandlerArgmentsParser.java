package org.yubing.datmv.config.xml.parser.argment;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.exception.MigrateException;
import org.yubing.datmv.util.DocumentUtils;

/**
 * 迁移配置参数解析
 * 
 * @author yubing
 *
 */
public class MigrateConfigMappingHandlerArgmentsParser implements ArgmentsParser {

	public static final String ATTR_TRANSPOSE = "transpose";
	
	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig,
			Element element) {
		String configFile = DocumentUtils.findAttrByName(element, ArgmentsParser.ATTR_VALUE, new String[]{"value"});
		if (StringUtils.isBlank(configFile)) {
			throw new MigrateException("the mvconfig mapping handler must config config file!");
		}

		Boolean transpose = false;
		String strTranspose = DocumentUtils.findAttrByName(element, ATTR_TRANSPOSE, new String[]{"value"});
		if (!StringUtils.isBlank(strTranspose)) {
			transpose = Boolean.valueOf(strTranspose);
		}
		
		return new Object[] {configFile, transpose };
	}

}

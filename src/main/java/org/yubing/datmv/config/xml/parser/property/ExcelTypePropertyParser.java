package org.yubing.datmv.config.xml.parser.property;

import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.PropertyParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.type.excel.ExcelReader;
import org.yubing.datmv.util.DocumentUtils;

public class ExcelTypePropertyParser implements PropertyParser {


	private static final String ATTR_HAS_HEADER = "has-header";

	public void parserProperties(XmlMigrateConfig xmlMigrateConfig, Object obj,
			Element element) {
		String strHasHeader = DocumentUtils.findAttrByName(element,
				ATTR_HAS_HEADER, new String[]{"value", "type"});
		
		if (obj instanceof ExcelReader) {
			ExcelReader excelReader= (ExcelReader)obj;
			excelReader.setHasHeader(Boolean.valueOf(strHasHeader));
		}
	}

}

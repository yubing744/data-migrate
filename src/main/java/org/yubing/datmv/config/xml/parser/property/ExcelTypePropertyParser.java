package org.yubing.datmv.config.xml.parser.property;

import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.PropertyParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.type.excel.ExcelReader;
import org.yubing.datmv.type.excel.ExcelWriter;
import org.yubing.datmv.util.DocumentUtils;

public class ExcelTypePropertyParser implements PropertyParser {


	private static final String ATTR_HAS_HEADER = "has-header";
	
	private static final String ATTR_START_ROW = "start-row";
	private static final String ATTR_START_COLUMN = "start-column";

	public void parserProperties(XmlMigrateConfig xmlMigrateConfig, Object obj,
			Element element) {
		if (obj instanceof ExcelReader) {
			ExcelReader excelReader= (ExcelReader)obj;
			
			String strHasHeader = DocumentUtils.findAttrByName(element,
					ATTR_HAS_HEADER, new String[]{"value", "type"});
			excelReader.setHasHeader(Boolean.valueOf(strHasHeader));
		}
		
		if (obj instanceof ExcelWriter) {
			ExcelWriter excelWriter= (ExcelWriter)obj;
			
			String strStartRow = DocumentUtils.findAttrByName(element,
					ATTR_START_ROW, new String[]{"value"});
			if (NumberUtils.isNumber(strStartRow)) {
				excelWriter.setStartRow(Integer.parseInt(strStartRow));
			}
			
			String strStartColumn = DocumentUtils.findAttrByName(element,
					ATTR_START_COLUMN, new String[]{"value"});
			if (NumberUtils.isNumber(strStartColumn)) {
				excelWriter.setStartColumn(Integer.parseInt(strStartColumn));
			}
		}
	}

}

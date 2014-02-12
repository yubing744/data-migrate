package org.yubing.datmv.config.xml.parser.argment;

import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DataSource;
import org.yubing.datmv.util.DocumentUtils;

public class JdbcWriterConstructorArgParser extends JdbcConstructorArgParser {

	public static final String ATTR_UPDATE = "update";
	
	@Override
	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig,
			Element element) {
		Element dataSourceEle = DocumentUtils.findOneElementByTagName(element,
				TAG_DATA_SOURCE);
		DataSource dataSource = parserDataSource(dataSourceEle);
		if (dataSource == null) {
			throw new RuntimeException("Error in paser " + element
					+ ", not found " + TAG_DATA_SOURCE);
		}

		String dialectClass = DocumentUtils.findAttrByName(element,
				ATTR_DIALECT_CLASS, new String[]{"value", "type"});

		String tableName = DocumentUtils.findAttrByName(element,
				ATTR_TABLE_NAME);

		String strUpdate = DocumentUtils.findAttrByName(element,
				ATTR_UPDATE);
		
		return new Object[] { dataSource, dialectClass, tableName, Boolean.valueOf(strUpdate)};
	}

 
}

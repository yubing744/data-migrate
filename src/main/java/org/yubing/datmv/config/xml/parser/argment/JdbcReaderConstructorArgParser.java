package org.yubing.datmv.config.xml.parser.argment;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DataSource;
import org.yubing.datmv.util.DocumentUtils;

public class JdbcReaderConstructorArgParser extends JdbcConstructorArgParser {

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

		String dialect = DocumentUtils.findAttrByName(element,
				ATTR_DIALECT_CLASS);

		String tableName = DocumentUtils.findAttrByName(element,
				ATTR_TABLE_NAME);

		String sql = DocumentUtils.findTextContentByName(element, ATTR_SQL);

		if (StringUtils.isBlank(tableName) && StringUtils.isBlank(sql)) {
			throw new RuntimeException("Error in paser " + element
					+ ", must config " +  ATTR_TABLE_NAME + " or " + ATTR_SQL + " one.");
		}

		if (StringUtils.isBlank(sql)) {
			return new Object[] { dataSource, dialect, "table", tableName};
		} else {
			return new Object[] { dataSource, dialect, "sql", sql};
		}
	}

}

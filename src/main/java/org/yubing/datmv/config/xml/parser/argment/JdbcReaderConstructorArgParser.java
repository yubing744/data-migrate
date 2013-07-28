package org.yubing.datmv.config.xml.parser.argment;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DataSource;
import org.yubing.datmv.util.DocumentUtils;

/**
 *	JDBC Reader构造参数解析器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public class JdbcReaderConstructorArgParser extends JdbcConstructorArgParser {

	@Override
	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig,
			Element element) {
		Object[] args = super.parserArgs(xmlMigrateConfig, element);

		DataSource dataSource = (DataSource)args[0];
		String dialectClass = (String)args[1];
		String tableName = (String)args[2];
		
		String sql = DocumentUtils.findTextContentByName(element, ATTR_SQL);

		if (StringUtils.isBlank(tableName) && StringUtils.isBlank(sql)) {
			throw new RuntimeException("Error in paser " + element
					+ ", must config " +  ATTR_TABLE_NAME + " or " + ATTR_SQL + " one.");
		}

		if (StringUtils.isBlank(sql) && !StringUtils.isBlank(tableName)) {
			return new Object[] { dataSource, dialectClass, "table", tableName};
		} else if (!StringUtils.isBlank(sql) && StringUtils.isBlank(tableName)){
			return new Object[] { dataSource, dialectClass, "sql", sql};
		} else {
			return new Object[] { dataSource, dialectClass, "both", tableName, sql};
		}
	}

}

package org.yubing.datmv.config.xml.parser.argment;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.util.DataSource;
import org.yubing.datmv.util.DocumentUtils;

/**
 * JDBC 类型读取器、写入器构造参数解析类
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class JdbcConstructorArgParser implements ArgmentsParser {

	public static final String TAG_DATA_SOURCE = "data-source";

	public static final String ATTR_DRIVER_CLASS = "driver-class";
	public static final String ATTR_URL = "url";
	public static final String ATTR_USERNAME = "username";
	public static final String ATTR_PASSWORD = "password";

	public static final String ATTR_TABLE_NAME = "table-name";
	public static final String ATTR_BASE_SQL = "base-sql";
	public static final String ATTR_DIALECT_CLASS = "dialect";

	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig, Element element) {

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

		String baseSql = DocumentUtils.findAttrByName(element, ATTR_BASE_SQL);

		if (StringUtils.isBlank(tableName) && StringUtils.isBlank(baseSql)) {
			throw new RuntimeException("Error in paser " + element
					+ ", must config "
					+ new String[] { ATTR_TABLE_NAME, ATTR_BASE_SQL } + " one.");
		}

		return new Object[] { dataSource, dialect, tableName, baseSql };
	}

	/**
	 * 解析数据源
	 * 
	 * @param element
	 * @return
	 */
	private DataSource parserDataSource(Element dataSourceEle) {
		DataSource ds = null;
		if (dataSourceEle != null) {
			ds = new DataSource();

			String driverClass = DocumentUtils.findAttrByName(dataSourceEle,
					ATTR_DRIVER_CLASS);
			if (!StringUtils.isBlank(driverClass)) {
				ds.setDriverClass(driverClass);
			} else {
				throw new RuntimeException("Error in paser " + dataSourceEle
						+ ", not found " + ATTR_DRIVER_CLASS);
			}

			String url = DocumentUtils.findAttrByName(dataSourceEle, ATTR_URL);
			if (!StringUtils.isBlank(driverClass)) {
				ds.setUrl(url);
			} else {
				throw new RuntimeException("Error in paser " + dataSourceEle
						+ ", not found " + ATTR_URL);
			}

			String username = DocumentUtils.findAttrByName(dataSourceEle,
					ATTR_USERNAME);
			if (!StringUtils.isBlank(driverClass)) {
				ds.setUsername(username);
			} else {
				throw new RuntimeException("Error in paser " + dataSourceEle
						+ ", not found " + ATTR_USERNAME);
			}

			String password = DocumentUtils.findAttrByName(dataSourceEle,
					ATTR_PASSWORD);
			if (!StringUtils.isBlank(driverClass)) {
				ds.setPassword(password);
			} else {
				throw new RuntimeException("Error in paser " + dataSourceEle
						+ ", not found " + ATTR_PASSWORD);
			}
		}

		return ds;
	}
}

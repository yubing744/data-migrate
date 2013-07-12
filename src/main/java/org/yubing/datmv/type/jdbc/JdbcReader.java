package org.yubing.datmv.type.jdbc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;
import org.yubing.datmv.util.DBHelper;
import org.yubing.datmv.util.DataSource;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 通过JDBC读取数据库表记录
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class JdbcReader implements PageReader {
	private DataSource dataSource;
	private String tableName;

	private DBHelper dbHelper;
	private String baseSql;
	private String querySql;

	private Dialect dialect;
	private String dialectClass;
	
	private int curLine = 0;
	private int totalLine;

	public JdbcReader(DataSource dataSource,  String dialectClass, String tableName) {
		this.dataSource = dataSource;
		this.dialectClass = dialectClass;
		this.tableName = tableName;
		this.querySql = null;
		
	}

	public JdbcReader(DataSource dataSource,  String dialectClass, String tableName, String querySql) {
		this.dataSource = dataSource;
		this.dialectClass = dialectClass;
		this.tableName = tableName;
		this.querySql = querySql;
	}

	public void open() {
		dbHelper = new DBHelper(dataSource);
		dialect = (Dialect)ReflectUtils.newInstance(dialectClass);
		
		baseSql = querySql;
		if (StringUtils.isBlank(baseSql)) {
			baseSql = "select * from " + this.tableName;
		}

		totalLine = findTotalLine();
	}

	protected int findTotalLine() {
		String countSql = "select count(*) from " + this.tableName;
		String numStr = dbHelper.querySingleData(countSql);
		int totalLine = Integer.parseInt(numStr);
		return totalLine;
	}

	public boolean hasNext() {
		return curLine < this.totalLine;
	}

	public RecordPage readPage(int pageSize) {
		int curLine = this.curLine;
		int totalLine = this.totalLine;

		int startLine = curLine;
		int endLine = curLine + pageSize;
		endLine = endLine > totalLine ? totalLine : endLine;

		RecordPage page = new SimpleRecordPage(pageSize);

		String sql = dialect.buildPageQuery(this.baseSql, startLine, endLine);
		List<Map<String, String>> dataPage = dbHelper.query(sql);

		int readSize = dataPage.size();
		if (dataPage != null) {
			for (int readLine = 0; readLine < readSize; readLine++) {
				Record record = new SimpleRecord();

				Map<String, String> datas = dataPage.get(readLine);
				if (datas != null) {
					for (Iterator<Entry<String, String>> it = datas.entrySet()
							.iterator(); it.hasNext();) {
						Entry<String, String> entry = it.next();
						String key = entry.getKey();
						String contents = entry.getValue();

						SimpleDataField cellData = new SimpleDataField();
						cellData.setName(key);
						cellData.setType("string");
						cellData.setData(contents);

						record.addDataField(key, cellData);
					}
				}
				page.writeRecord(record);
			}

			this.curLine = this.curLine + readSize;
		}

		return page;
	}

	public void release() {

	}

}

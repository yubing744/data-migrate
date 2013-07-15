package org.yubing.datmv.type.jdbc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.core.DataType;
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
	private String type;
	private String baseSql;
	private String sql;

	private Dialect dialect;
	private String dialectClass;
	
	private int curLine = 0;
	private int totalLine;
	
	private Map<Class<?>, String> supportTypes = new HashMap<Class<?>, String>();


	public JdbcReader(DataSource dataSource,  String dialectClass, String type, String typeVal) {
		this.dataSource = dataSource;
		this.dialectClass = dialectClass;
		
		this.type = type;
		
		if ("table".equals(type)) {
			this.tableName = typeVal;
		} else {
			this.sql = typeVal;
		}
		
		init();
	}

	protected void init() {
		supportTypes.put(String.class, DataType.STRING);
		
		supportTypes.put(Short.class, DataType.SHORT);
		supportTypes.put(Integer.class, DataType.INTEGER);
		supportTypes.put(Long.class, DataType.LONG);
		supportTypes.put(Float.class, DataType.FLOAT);
		supportTypes.put(Double.class, DataType.DOUBLE);
		
		supportTypes.put(Character.class, DataType.CHARACTER);
		supportTypes.put(Boolean.class, DataType.BOOLEAN);
	}
	
	
	public void open() {
		dbHelper = new DBHelper(dataSource);
		dialect = (Dialect)ReflectUtils.newInstance(dialectClass);
		
		if ("table".equals(type)) {
			baseSql = "select * from `" + this.tableName + "`";
		} else {
			baseSql = sql;
		}

		totalLine = findTotalLine();
	}

	protected int findTotalLine() {
		String countSql = "select count(t.id) from (" + this.baseSql + ") t";
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

		String sql = dialect.buildPageQuery(this.baseSql, startLine, pageSize);
		List<Map<String, Object>> dataPage = dbHelper.queryBySQL(sql);

		int readSize = dataPage.size();
		if (dataPage != null) {
			for (int readLine = 0; readLine < readSize; readLine++) {
				Record record = new SimpleRecord();

				Map<String, Object> datas = dataPage.get(readLine);
				if (datas != null) {
					for (Iterator<Entry<String, Object>> it = datas.entrySet()
							.iterator(); it.hasNext();) {
						Entry<String, Object> entry = it.next();
						String key = entry.getKey();
						Object contents = entry.getValue();

						SimpleDataField cellData = null;
						
						if (contents != null) {
							String type = supportTypes.get(contents.getClass());
							if (type != null) {
								cellData = new SimpleDataField(key, type);
								cellData.setData(contents);
							} else {
								cellData = new SimpleDataField(key, DataType.STRING);
								cellData.setData(contents);	
							}
						} else {
							cellData = new SimpleDataField(key, DataType.STRING);
							cellData.setData(null);
						}
						
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

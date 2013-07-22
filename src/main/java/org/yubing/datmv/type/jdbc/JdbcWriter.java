package org.yubing.datmv.type.jdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.util.DBHelper;
import org.yubing.datmv.util.DataSource;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 通过JDBC向数据库表插入记录
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class JdbcWriter implements PageWriter {

	private DataSource dataSource;
	private String tableName;

	private DBHelper dbHelper;
	private String baseSql;
	private String modifySql;
	private Dialect dialect;
	private String dialectClass;
	

	public JdbcWriter(DataSource dataSource, String dialectClass, String tableName) {
		this.dataSource = dataSource;
		this.tableName = tableName;
		this.modifySql = null;
		this.dialectClass = dialectClass;
	}
	
	public JdbcWriter(DataSource dataSource,String dialectClass, String tableName, String modifySql) {
		this.dataSource = dataSource;
		this.tableName = tableName;
		this.modifySql = modifySql;
		this.dialectClass = dialectClass;
	}

	public void open(MigrateContext context) {
		dbHelper = new DBHelper(dataSource);
		dialect = (Dialect)ReflectUtils.newInstance(dialectClass);
		
		baseSql = modifySql;
		if (StringUtils.isBlank(baseSql)) {
			baseSql = "insert into `" + this.tableName + "` ";
		}
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			writeRecord(record);
		}
	}

	private void writeRecord(Record record) {
		StringBuilder insertSql = new StringBuilder(baseSql);

		StringBuilder colDatas = new StringBuilder("(");
		StringBuilder valDatas = new StringBuilder("(");
		List<Object> args = new ArrayList<Object>();
		
		if (record != null) {
			Set<String> keys = record.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				DataField dataField = record.getDataField(key);
				
				if (dataField != null) {
					colDatas.append(dataField.getName()).append(",");
					valDatas.append("?").append(",");
					args.add(dataField.getData());
				}
			}
			if (colDatas.length() > 1) {
				colDatas.setLength(colDatas.length() - 1);
			}
			colDatas.append(")");

			if (valDatas.length() > 1) {
				valDatas.setLength(valDatas.length() - 1);
			}
			valDatas.append(")");
		}

		insertSql.append(colDatas).append(" values").append(valDatas);

		boolean success = dbHelper.update(insertSql.toString(), args.toArray(new Object[args.size()]));
		if (!success) {
			throw new RuntimeException("Error in sql:" + insertSql);
		}
	}

	public void release() {
		// TODO Auto-generated method stub

	}
}

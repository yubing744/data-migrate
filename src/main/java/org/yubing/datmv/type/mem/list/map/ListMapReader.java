package org.yubing.datmv.type.mem.list.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;

/**
 *	列表Map读取器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-26
 */
public class ListMapReader extends ArrayList<Map<String, Object>> implements PageReader {

	private static final long serialVersionUID = 611957371757805946L;

	private int curLine = 0;
	
	private Map<Class<?>, String> supportTypes = new HashMap<Class<?>, String>();
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
	
	public ListMapReader() {
		
	}
	
	public ListMapReader(List<Map<String, Object>> datas) {
		this.addAll(datas);
	}
	
	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public RecordPage readPage(int pageSize) {
		int curLine = this.curLine;
		
		RecordPage page = new SimpleRecordPage(pageSize);
		
		List<Map<String, Object>> dataPage = this; 
		
		int readSize = 0;
		int endLine = (curLine + pageSize) > dataPage.size() ? dataPage.size() : curLine + pageSize;
		
		for (int readLine = curLine; readLine < endLine; readLine++) {
			readSize++;
			
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
					
					record.addDataField(cellData);
				}
			}
			
			page.writeRecord(record);
		}

		this.curLine = this.curLine + readSize;
		return page;
	}

	public boolean hasNext() {
		return this.size() > this.curLine;
	}


	public void release() {
		// TODO Auto-generated method stub
		
	}
}

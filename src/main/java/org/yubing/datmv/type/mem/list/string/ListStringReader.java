package org.yubing.datmv.type.mem.list.string;

import java.util.ArrayList;
import java.util.List;

import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;

/**
 *	列表字符串读取器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-29
 */
public class ListStringReader extends ArrayList<String> implements PageReader{

	private static final long serialVersionUID = 819153871440414694L;

	private int curLine = 0;

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public RecordPage readPage(int pageSize) {
		int curLine = this.curLine;
		
		RecordPage page = new SimpleRecordPage(pageSize);
		
		List<String> dataPage = this; 
		
		int readSize = 0;
	 
		for (int readLine = curLine; readLine < this.size(); readLine++) {
			readSize++;
			
			Record record = new SimpleRecord();

			String data = dataPage.get(readLine);

			SimpleDataField  cellData = new SimpleDataField("data", DataType.STRING);
			cellData.setData(data);
			
			record.addDataField(cellData);
			
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

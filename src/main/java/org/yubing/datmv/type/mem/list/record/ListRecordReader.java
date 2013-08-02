package org.yubing.datmv.type.mem.list.record;

import java.util.List;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleRecordPage;
import org.yubing.datmv.exception.MigrateException;

public class ListRecordReader implements PageReader {

	private int curLine = 0;
	
	private List<Record> records;
	
	public ListRecordReader(List<Record> records) {
		if (records == null) {
			throw new MigrateException("records can not be empty!");
		}
		
		this.records = records;
	}
	
	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public RecordPage readPage(int pageSize) {
		int curLine = this.curLine;
		
		RecordPage page = new SimpleRecordPage(pageSize);
		
		List<Record> dataPage = records; 
		
		int readSize = 0;
		int endLine = (curLine + pageSize) > dataPage.size() ? dataPage.size() : curLine + pageSize;
		
		for (int readLine = curLine; readLine < endLine; readLine++) {
			readSize++;
			
			Record record = dataPage.get(readLine);
			page.writeRecord(record);
		}

		this.curLine = this.curLine + readSize;
		return page;
	}

	public boolean hasNext() {
		return this.records.size() > this.curLine;
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}
}


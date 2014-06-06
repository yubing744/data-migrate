package org.yubing.datmv.type.mem.list.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleRecordPage;

/**
 * 列表记录读取器
 * 
 * @author yubing
 *
 */
public class ListRecordReader extends ArrayList<Record> implements PageReader {

	private static final long serialVersionUID = 8173598979632136718L;

	private int curLine = 0;
	
	public ListRecordReader() {
		super();
	}

	public ListRecordReader(Collection<? extends Record> c) {
		super(c);
	}

	public ListRecordReader(int initialCapacity) {
		super(initialCapacity);
	}

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public RecordPage readPage(int pageSize) {
		int curLine = this.curLine;
		
		RecordPage page = new SimpleRecordPage(pageSize);
		
		List<Record> dataPage = this; 
		
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
		return this.size() > this.curLine;
	}

	public void release() {
		
	}
}


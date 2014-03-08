package org.yubing.datmv.type.mem.list.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;

public class ListEntityReader extends ArrayList<Object> implements PageReader{

	public static final String TIEM_KEY_NAME = "item";

	private static final long serialVersionUID = 819153871440414694L;

	private int curLine = 0;

	
	public ListEntityReader() {
		super();
	}


	public ListEntityReader(Collection<? extends Object> c) {
		super(c);
	}

	public void open(MigrateContext ctx) {
		ctx.setAttribute("total.record", this.size());
	}

	public RecordPage readPage(int pageSize) {
		int curLine = this.curLine;
		
		RecordPage page = new SimpleRecordPage(pageSize);
		
		List<Object> dataPage = this; 
		
		int readSize = 0;
	 
		for (int readLine = curLine; readLine < this.size() && readLine<curLine + pageSize; readLine++) {
			readSize++;
			
			Record record = new SimpleRecord();

			Object entity = dataPage.get(readLine);
			
			if (entity != null) {
				SimpleDataField cellData = new SimpleDataField(TIEM_KEY_NAME, DataType.OBJECT);
				cellData.setData(entity);
				
				record.addDataField(cellData);
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


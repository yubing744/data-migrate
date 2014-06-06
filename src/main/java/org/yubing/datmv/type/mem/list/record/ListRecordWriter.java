package org.yubing.datmv.type.mem.list.record;

import java.util.ArrayList;
import java.util.Collection;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

/**
 * 列表记录写入器
 * 
 * @author yubing
 *
 */
public class ListRecordWriter extends ArrayList<Record> implements PageWriter {

	private static final long serialVersionUID = -3336237452354532878L;

	public ListRecordWriter() {
		super();
	}

	public ListRecordWriter(Collection<? extends Record> c) {
		super(c);
	}

	public ListRecordWriter(int initialCapacity) {
		super(initialCapacity);
	}

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			this.add(record);
		}
	}
	
	public void release() {
		// TODO Auto-generated method stub
		
	}

}

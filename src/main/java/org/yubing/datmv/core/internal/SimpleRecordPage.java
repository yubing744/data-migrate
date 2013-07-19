package org.yubing.datmv.core.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

public class SimpleRecordPage implements RecordPage {
	
	private List<Record> records;
	private Iterator<Record> rIt;

	public SimpleRecordPage() {
		this.records = new ArrayList<Record>();
	}

	public SimpleRecordPage(int pageSize) {
		this.records = new ArrayList<Record>(pageSize);
	}

	protected Iterator<Record> getRIt() {
		if (rIt == null) {
			rIt = records.iterator();
		}
		return rIt;
	}

	public boolean hasNext() {
		return getRIt().hasNext();
	}

	public Record readRecord() {
		return getRIt().next();
	}

	public void writeRecord(Record record) {
		records.add(record);
	}

	public void reset() {
		rIt = null;
	}

	public int pageSize() {
		return records.size();
	}

	public void open(MigrateContext context) {
	
	}

	public void release() {
		this.reset();
		records.clear();
	}

	/* (non-Javadoc)
	 * @see org.yubing.datmv.core.RecordPage#getFieldDatas(java.lang.String)
	 */
	public Object[] getFieldDatas(String fieldName) {
		this.reset();

		Object[] objs = new Object[this.pageSize()];
		
		int index = 0;
		while (this.hasNext()) {
			Record record = this.readRecord();
			objs[index++] = record.getFieldData(fieldName);
		}
		
		return objs;
	}

}

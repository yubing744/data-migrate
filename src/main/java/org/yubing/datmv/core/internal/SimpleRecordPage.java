package org.yubing.datmv.core.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public void open() {
	
	}

	public void release() {
		this.reset();
		records.clear();
	}

}

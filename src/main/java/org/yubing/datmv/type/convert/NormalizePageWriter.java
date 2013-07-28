package org.yubing.datmv.type.convert;

import java.util.Iterator;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.ListRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;

public class NormalizePageWriter implements PageWriter {

	protected PageWriter pageWriter;
	
	public NormalizePageWriter(PageWriter pageWriter) {
		this.pageWriter = pageWriter;
	}
	
	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		SimpleRecordPage toPage = new SimpleRecordPage();
		
		writePage(recPage, toPage);
		
		this.pageWriter.writePage(toPage);
	}

	protected void writePage(RecordPage recPage, SimpleRecordPage toPage) {
		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			
			if (record != null) {
				writeRecord(record, toPage);
			}
		}
	}

	protected void writeRecord(Record record, SimpleRecordPage toPage) {
		Record toRecord = new ListRecord();
		
		for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
			DataField dataField = it.next();
			writeDataField(dataField, toRecord, toPage);
		}
		
		toPage.writeRecord(toRecord);
	}

	protected void writeDataField(DataField dataField, Record toRecord, SimpleRecordPage toPage) {
		Object data = dataField.getData();
		
		if (data instanceof RecordPage) {
			RecordPage page = (RecordPage)data;
			writePage(page, toPage);
		} else {
			toRecord.addDataField(dataField);
		}	
	}

	public void release() {
		// TODO Auto-generated method stub
	}
}

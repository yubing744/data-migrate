package org.yubing.datmv.type.convert;

import java.util.Iterator;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.AliasRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;

public class TransposePageWriter implements PageWriter {

	protected PageWriter pageWriter;
	
	public TransposePageWriter(PageWriter pageWriter) {
		this.pageWriter = pageWriter;
	}
	
	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		SimpleRecordPage toPage = new SimpleRecordPage();
		
		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			
			if (record != null) {
				toPage.reset();
				
				for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
					DataField dataField = it.next();
					
					Record toRecord = null;
					
					if (toPage.hasNext()) {
						toRecord = toPage.readRecord();
					} else {
						toRecord = new AliasRecord();
						toPage.writeRecord(toRecord);
						toPage.reset();
					}
					
					toRecord.addDataField(dataField);
				}
			}
		}
		
		this.pageWriter.writePage(toPage);
	}

	public void release() {
		// TODO Auto-generated method stub
	}
}

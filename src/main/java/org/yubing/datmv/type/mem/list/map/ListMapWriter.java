package org.yubing.datmv.type.mem.list.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

public class ListMapWriter extends ArrayList<Map<String, Object>>implements PageWriter{

	private static final long serialVersionUID = 8568903409287357925L;

	public void open(MigrateContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			writeRecord(record);
		}
	}

	private void writeRecord(Record record) {
		if (record != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
				DataField dataField = it.next();
				map.put(dataField.getName(), dataField.getData());
			}
			
			this.add(map);
		}
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}
}

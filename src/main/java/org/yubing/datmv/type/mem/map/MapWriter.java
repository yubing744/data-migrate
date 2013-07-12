package org.yubing.datmv.type.mem.map;

import java.util.Map;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

public class MapWriter implements PageWriter {

	public static final String KEY_KEY = "key";
	public static final String VALUE_KEY = "value";

	private Map<String, Object> map;

	public MapWriter(Map<String, Object> map) {
		this.map = map;
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		try {
			while (recPage.hasNext()) {
				Record record = recPage.readRecord();
				writeRecord(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeRecord(Record record) {
		if (record != null && map != null) {
			DataField keyField = record.getDataField(KEY_KEY);
			DataField valField = record.getDataField(VALUE_KEY);
			if (keyField != null && valField != null) {
				map.put(keyField.getData(), valField.getData());
			}
		}
	}

	public void open() {
		// TODO 自动生成方法存根

	}

	public void release() {
		// TODO 自动生成方法存根

	}

}

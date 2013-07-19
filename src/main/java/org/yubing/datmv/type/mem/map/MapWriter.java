package org.yubing.datmv.type.mem.map;

import java.util.HashMap;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

public class MapWriter<K, V> extends HashMap<K, V> implements PageWriter {

	private static final long serialVersionUID = -3963869873572840466L;
	public static final String KEY_KEY = "key";
	public static final String VALUE_KEY = "value";


	public MapWriter() {

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

	@SuppressWarnings("unchecked")
	private void writeRecord(Record record) {
		if (record != null) {
			DataField keyField = record.getDataField(KEY_KEY);
			DataField valField = record.getDataField(VALUE_KEY);
			
			if (keyField != null && valField != null) {
				this.put((K)keyField.getData(), (V)valField.getData());
			}
		}
	}

	public void open(MigrateContext context) {
		// TODO 自动生成方法存根

	}

	public void release() {
		// TODO 自动生成方法存根

	}

}
